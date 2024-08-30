package com.project.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private Long userId; // 사용자ID

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 300, message = "Password must be between 8 and 300 characters")
    private String userPw; // 사용자PW => 암호화 => SNS 로그인 시 필요x

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email must be 60 characters or less")
    private String email; // 이메일

    @Size(max = 200, message = "Profile path must be 200 characters or less")
    private String profilePath; // 프로필 사진

    @Size(max = 20, message = "Profile size must be 20 characters or less")
    private String profileSize; // 프로필 크기

    @Size(max = 20, message = "Profile type must be 20 characters or less")
    private String profileType; // 프로필 사진 유형 ex) png, jpeg, xml, svg 등

    @NotBlank(message = "Nickname is mandatory")
    @Size(max = 10, message = "Nickname must be 10 characters or less")
    private String nickname; // 닉네임

    @Size(max = 40, message = "Description must be 40 characters or less")
    private String description; // 자기소개

    @NotNull(message = "Board count is mandatory")
    private Integer boardCount; // 작성한 게시글 갯수

    @NotNull(message = "News count is mandatory")
    private Integer newsCount; // 작성한 뉴스 갯수

    @NotNull(message = "Comment count is mandatory")
    private Integer commentCount; // 작성한 댓글 갯수

    @NotNull(message = "Like count is mandatory")
    private Integer likeCount; // 좋아요 누른 갯수

    @NotNull(message = "Follower count is mandatory")
    private Integer followerCount; // 팔로워 수(상대가)

    @NotNull(message = "Following count is mandatory")
    private Integer followingCount; // 팔로잉 수(내가)

    @NotNull(message = "Disable follow status is mandatory")
    private Boolean disableFollow; // 팔로우 차단 설정 상태

    @NotNull(message = "Private account status is mandatory")
    private Boolean privateAccount; // 비공개 설정 상태

    @NotNull(message = "Notice notification status is mandatory")
    private Boolean noticeNotification; //공지사항 설정 상태

    @NotNull(message = "Comment notification status is mandatory")
    private Boolean commentNotification; //댓글 설정 상태

    @NotNull(message = "Like notification status is mandatory")
    private Boolean likeNotification; //좋아요 설정 상태

    @NotNull(message = "Recommend notification status is mandatory")
    private Boolean recommendNotification; //추천 뉴스/게시글 설정 상태

    @NotNull(message = "Follow notification status is mandatory")
    private Boolean followNotification; //팔로우 설정 상태

    @NotNull(message = "Event notification status is mandatory")
    private Boolean eventNotification; //이벤트 설정 상태

    @NotNull(message = "Deleted status is mandatory")
    private Boolean deleted; //계정 삭제 상태

    private Timestamp deletedTime; //삭제된 시간

    private Timestamp createdTime; //생성된 시간

}
