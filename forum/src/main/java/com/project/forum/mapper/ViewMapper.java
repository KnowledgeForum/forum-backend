package com.project.forum.mapper;

import com.project.forum.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ViewMapper {
    ViewMapper INSTANCE = Mappers.getMapper(ViewMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "board", target = "board")
    @Mapping(target = "createdTime", ignore = true)
    BoardView toEntity(AppUser user, Board board);
}
