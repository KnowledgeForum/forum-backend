package com.project.forum.service;

import com.project.forum.dto.JoinAppUserDto;
import com.project.forum.dto.ResponseOtherUserDto;
import com.project.forum.entity.AppUser;
import com.project.forum.exception.CustomException;
import com.project.forum.exception.ErrorCode;
import com.project.forum.mapper.AppUserMapper;
import com.project.forum.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppUserService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 7;

    private final AppUserRepository appUserRepository;

    private final EmailService emailService;

    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public ResponseOtherUserDto getOtherUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return AppUserMapper.INSTANCE.toResponseOtherUserDto(user);
    }

    public boolean save(JoinAppUserDto joinAppUserDto){
        AppUser appUser = AppUser.builder()
                .email(joinAppUserDto.getEmail())
                .userPw(joinAppUserDto.getUserPw())
                .nickname(joinAppUserDto.getNickname())
                .noticeNotification(joinAppUserDto.isEnableNotification())
                .commentNotification(joinAppUserDto.isEnableNotification())
                .likeNotification(joinAppUserDto.isEnableNotification())
                .recommendNotification(joinAppUserDto.isEnableNotification())
                .followNotification(joinAppUserDto.isEnableNotification())
                .eventNotification(joinAppUserDto.isEnableEvent())
                .build();

        AppUser savedUser = appUserRepository.save(appUser);

        return savedUser != null;
    }

    // 이메일 인증 코드 관련
    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "Forum 이메일 인증 번호";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode);

        // 이메일 인증 요청 시 인증 번호 Resid에 저장 (key="AuthCode " + Email / value=AuthCode)
        redisService.setValues(AUTH_CODE_PREFIX + toEmail, authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    // 이메일 인증 코드 관련
    private void checkDuplicatedEmail(String email) {
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        System.out.println("checkDuplicatedEmail : "+appUser.toString()); // 없어야함
        if (appUser.isPresent()) {
            log.debug("AppUserServiceImpl.checkDuplicatedEmail exception occur email : {}", email);
            throw new CustomException(ErrorCode.FOUND_VERIFIED_EMAIL);
        }
    }

    private String createCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public void verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);

        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        if (!authResult) {
            throw new CustomException(ErrorCode.AUTH_CODE_IS_NOT_SAME);
        }
    }

}
