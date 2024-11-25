package com.project.forum.mapper;

import com.project.forum.dto.tag.TagDto;
import com.project.forum.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(source = "tag.tagId", target = "tagId")
    @Mapping(source = "tag.tagName", target = "tagName")
    TagDto.Response.Tag toTagWithoutCount(Tag tag);
}
