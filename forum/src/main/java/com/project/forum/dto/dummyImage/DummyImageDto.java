package com.project.forum.dto.dummyImage;

import com.project.forum.validation.ValidFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class DummyImageDto {

    public static class Response {
        @Getter
        @Setter
        @AllArgsConstructor
        public static class Image {
            private final Long imageId;
            private final String path;
        }
    }
}
