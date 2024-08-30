package com.project.forum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RequestUpdateProfileDto {
//    @ValidFile(message = "프로필 사진은 필수입니다.")
    private final MultipartFile profile;

    @JsonCreator
    public RequestUpdateProfileDto(MultipartFile profile) {
        this.profile = profile;
    }
}