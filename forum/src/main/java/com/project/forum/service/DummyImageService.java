package com.project.forum.service;

import com.project.forum.dto.FileDto;
import com.project.forum.dto.dummyImage.DummyImageDto;
import com.project.forum.entity.DummyImage;
import com.project.forum.mapper.DummyImageMapper;
import com.project.forum.repository.DummyImageRepository;
import com.project.forum.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DummyImageService {
    private final DummyImageRepository dummyImageRepository;

    private final FileStore fileStore;

    public DummyImageDto.Response.Image createImage(final MultipartFile image) {
        FileDto file = fileStore.storeFile("dummies/", image);
        DummyImage dummyImage = dummyImageRepository.save(DummyImageMapper.INSTANCE.toEntity(file));

        return DummyImageMapper.INSTANCE.toImage(dummyImage);
    }
}
