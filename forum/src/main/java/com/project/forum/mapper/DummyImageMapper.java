package com.project.forum.mapper;

import com.project.forum.dto.FileDto;
import com.project.forum.dto.dummyImage.DummyImageDto;
import com.project.forum.entity.DummyImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DummyImageMapper {
    DummyImageMapper INSTANCE = Mappers.getMapper(DummyImageMapper.class);

    @Mapping(source = "image.size", target = "imageSize")
    @Mapping(source = "image.extension", target = "imageType")
    @Mapping(source = "image.storePath", target = "imagePath")
    DummyImage toEntity(FileDto image);

    @Mapping(source = "image.imageId", target = "imageId")
    @Mapping(source = "image.imagePath", target = "path")
    DummyImageDto.Response.Image toImage(DummyImage image);
}
