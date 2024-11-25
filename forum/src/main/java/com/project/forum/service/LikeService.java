package com.project.forum.service;

import com.project.forum.entity.AppUser;
import com.project.forum.entity.Board;
import com.project.forum.entity.BoardLike;
import com.project.forum.exception.CustomException;
import com.project.forum.exception.ErrorCode;
import com.project.forum.mapper.LikeMapper;
import com.project.forum.repository.AppUserRepository;
import com.project.forum.repository.BoardRepository;
import com.project.forum.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final AppUserRepository appUserRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createLike(Long userId, Long boardId) {
        AppUser user = appUserRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        BoardLike like = likeRepository.findByUserAndBoard(user, board).orElse(null);

        if (like == null) {
            board.setLikeCount(board.getLikeCount() + 1);

            boardRepository.save(board);
            likeRepository.save(LikeMapper.INSTANCE.toEntity(user, board));
        }
    }

    @Transactional
    public void deleteLike(Long userId, Long boardId) {
        AppUser user = appUserRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        BoardLike like = likeRepository.findByUserAndBoard(user, board).orElse(null);

        if (like != null) {
            board.setLikeCount(board.getLikeCount() - 1);

            boardRepository.save(board);
            likeRepository.delete(like);
        }
    }
}
