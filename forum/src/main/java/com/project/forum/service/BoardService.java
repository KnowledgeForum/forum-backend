package com.project.forum.service;

import com.project.forum.dto.FileDto;
import com.project.forum.dto.board.BoardDto;
import com.project.forum.entity.*;
import com.project.forum.exception.CustomException;
import com.project.forum.exception.ErrorCode;
import com.project.forum.mapper.BoardImageMapper;
import com.project.forum.mapper.BoardMapper;
import com.project.forum.mapper.BoardTagMapper;
import com.project.forum.repository.*;

import com.project.forum.util.FileStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final AppUserRepository appUserRepository;
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;
    private final DummyImageRepository dummyImageRepository;
    private final BoardImageRepository boardImageRepository;

    private final FileStore fileStore;

    private final String storePath = "board/";

    @Transactional
    public Long create(final Long userId, final BoardDto.Request request) {
        AppUser user = appUserRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (user.getDeleted()) {
            throw new CustomException(ErrorCode.DELETED_USER);
        }

        Board board = null;

        if (request.getThumbnail() != null) {
            FileDto file = fileStore.storeFile(storePath, request.getThumbnail());
            board = BoardMapper.INSTANCE.toEntity(user, request, file);
        } else {
            board = BoardMapper.INSTANCE.toEntity(user, request, null);
        }

        Board createdBoard = boardRepository.save(board);

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());
        if (tags.size() != request.getTagIds().size() || tags.isEmpty() || tags.size() > 3) {
            throw new CustomException(ErrorCode.BAD_REQUEST_TAG);
        }
        tags.forEach(tag -> tag.setTagCount(tag.getTagCount() + 1));

        List<BoardTag> boardTags = tags.stream().map(tag -> BoardTagMapper.INSTANCE.toEntity(createdBoard, tag)).toList();
        boardTagRepository.saveAll(boardTags);

        if (request.getImageIds() != null) {
            List<DummyImage> dummyImages = dummyImageRepository.findAllByImageIdIn(request.getImageIds());
            if (dummyImages.size() != request.getImageIds().size()) {
                throw new CustomException(ErrorCode.BAD_REQUEST_IMAGE);
            }

            List<BoardImage> boardImages = dummyImages.stream().map(image -> BoardImageMapper.INSTANCE.toEntity(createdBoard, image)).toList();
            boardImageRepository.saveAll(boardImages);
            dummyImageRepository.deleteAllInBatch(dummyImages);
        }

        return createdBoard.getBoardId();
    }
}
