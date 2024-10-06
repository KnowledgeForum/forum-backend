package com.project.forum.dto;

import com.project.forum.entity.AppUser;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    // 인증된 사용자 정보만 필요 => name, email, picture 필드만 선언
    private String nickname;
    private String email;
    private String profilePath;

    public SessionUser(AppUser user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profilePath = user.getProfilePath();
    }
}
