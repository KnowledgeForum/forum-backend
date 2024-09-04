package com.project.forum.mapper;

import com.project.forum.dto.FileDto;
import com.project.forum.dto.board.BoardDto;
import com.project.forum.entity.AppUser;
import com.project.forum.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CustomTimestampTranslator.class)
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(source = "user", target = "uploader")
    @Mapping(source = "request.boardType", target = "category")
    @Mapping(source = "request.title", target = "title")
    @Mapping(source = "request.content", target = "content")
    @Mapping(source = "file.storePath", target = "thumbnailPath")
    @Mapping(source = "file.extension", target = "thumbnailType")
    @Mapping(source = "file.size", target = "thumbnailSize")
    Board toEntity(AppUser user, BoardDto.Request request, FileDto file);
}
