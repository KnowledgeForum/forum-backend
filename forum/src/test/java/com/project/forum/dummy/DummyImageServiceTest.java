package com.project.forum.dummy;

import com.project.forum.AbstractTest;
import com.project.forum.dto.FileDto;
import com.project.forum.repository.DummyImageRepository;
import com.project.forum.service.DummyImageService;
import com.project.forum.util.FileStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@ExtendWith({ MockitoExtension.class })
public class DummyImageServiceTest extends AbstractTest {
    @Mock
    private FileStore fileStore;
    @Mock
    private DummyImageRepository dummyImageRepository;
    @InjectMocks
    private DummyImageService dummyImageService;

    @Test
    @DisplayName("더미 이미지 추가")
    void createDummyImage() {
        Mockito.when(fileStore.storeFile(Mockito.anyString(), Mockito.any(MultipartFile.class)))
                .thenReturn(FileDto.builder()
                        .storeFileName("test.jpg")
                        .originalFileName("test.jpg")
                        .storePath("test.jpg")
                        .size(1024L)
                        .extension("image/jpeg")
                        .build()
                );
        Mockito.when(dummyImageRepository.save(Mockito.any())).thenReturn(super.getDummyImage());

        dummyImageService.createImage(super.getMockMultipartFile());

        Mockito.verify(dummyImageRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
