package com.project.forum.dto.board;

import com.project.forum.dto.appuser.AppUserDto;
import com.project.forum.dto.tag.TagDto;
import com.project.forum.type.BoardTypeEnum;
import com.project.forum.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;
import java.util.List;

public class BoardDto {
    @Getter
    public static class Request {
        @EnumValue(enumClass = BoardTypeEnum.class, message = "게시판 유형은 뉴스 또는 게시판이어야 합니다.", ignoreCase = true)
        private final String boardType;

        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 20, message = "제목은 20자 이하여야 합니다.")
        private final String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private final String content;

        @NotNull(message = "태그를 1 ~ 3개 선택해주세요.")
        @Size(min = 1, max = 3, message = "태그를 1 ~ 3개 선택해주세요.")
        private final List<Long> tagIds;

        private final MultipartFile thumbnail;
        private final List<Long> imageIds;

        // Test Code And Formdata에서 Dto를 자동 매핑하지 못해서 다음과 같은 코드로 변경
        @ConstructorProperties({"boardType", "title", "content", "tagIds", "thumbnail", "imageIds"})
        public Request(String boardType, String title, String content, List<Long> tagIds, MultipartFile thumbnail, List<Long> imageIds) {
            this.boardType = boardType;
            this.title = title;
            this.content = content;
            this.tagIds = tagIds;
            this.thumbnail = thumbnail;
            this.imageIds = imageIds;
        }
    }

    public static class Response {

        @Getter
        @Setter
        @AllArgsConstructor
        public static class Boards {

            @Getter
            @Setter
            @AllArgsConstructor
            public static class Board {
                private Long boardId;
                private String thumbnail;
                private String title;
                private List<TagDto.Response.Tag> tags;
                private Boolean isLike;
                private AppUserDto.Response.Intro uploader;
                private Long viewCount;
                private Long likeCount;
                private Long commentCount;
                private String createdTime;
            }

            private List<Board> boards;
            private Long total;
        }
    }
}
