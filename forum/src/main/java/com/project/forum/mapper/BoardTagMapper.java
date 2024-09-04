package com.project.forum.mapper;

import com.project.forum.entity.Board;
import com.project.forum.entity.BoardTag;
import com.project.forum.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardTagMapper {
    BoardTagMapper INSTANCE = Mappers.getMapper(BoardTagMapper.class);

    @Mapping(source = "board", target = "board")
    @Mapping(source = "tag", target = "tag")
    BoardTag toEntity(Board board, Tag tag);
}
