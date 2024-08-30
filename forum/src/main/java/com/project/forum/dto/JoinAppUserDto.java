package com.project.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinAppUserDto {

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 300, message = "Password must be between 8 and 300 characters")
    private String userPw; // 사용자PW => 암호화 => SNS 로그인 시 필요x

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email must be 60 characters or less")
    private String email; // 이메일

    @NotBlank(message = "Nickname is mandatory")
    @Size(max = 10, message = "Nickname must be 10 characters or less")
    private String nickname; // 닉네임

    private boolean isEnableNotification; // 일반 알림 여부
    
    private boolean isEnableEvent; // 이벤트성 알림 여부


}
