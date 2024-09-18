package com.project.forum.controller;

import com.project.forum.dto.AppUserDto;
import com.project.forum.dto.JoinAppUserDto;
import com.project.forum.dto.RequestLoginDto;
import com.project.forum.dto.RequestUpdateProfileDto;
import com.project.forum.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@ResponseBody
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOtherUser(@PathVariable(value = "user_id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getOtherUser(userId));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> publicLogin(@Valid @RequestBody RequestLoginDto dto) {
        // NOTE: Validation 에러 사용법을 보여주기 위해 미리 작성된 API입니다.
        // TODO: 1. 회원가입을 먼저 처리하세요.
        // TODO: 1. 로그인 로직을 작성하세요.
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileImg(@Valid @ModelAttribute RequestUpdateProfileDto dto) {
        // NOTE: Validation 에러 사용법을 보여주기 위해 미리 작성된 API입니다.
        // TODO: 1. 사용자 인증(세션)을 먼저 처리 하세요.
        // TODO: 2. Firebase Storage을 이용하여 기존 이미지를 삭제하고, 새로운 이미지를 업로드하는 로직을 작성하세요.
        return ResponseEntity.noContent().build();
    }

    @PostMapping//(value = "/")
    public ResponseEntity<?> saveUser(@Valid @RequestBody JoinAppUserDto joinAppUserDto) {
        // TODO: 회원가입시 사용자가 입력한 데이터 유효성 체크
        // TODO: 인증코드 완료 후 회원가입이 가능

        if (appUserService.save(joinAppUserDto))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().body("Invalid request parameters");

    }

    // 이메일 인증코드 전송
    @PostMapping(value = "/verify")
    public ResponseEntity sendMessage(@RequestParam("email") @Valid String email) {
        appUserService.sendCodeToEmail(email); // 중복 이메일 확인 코드 포함
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 이메일 인증코드 확인
    @PostMapping(value = "/verify/compare")
    public ResponseEntity<?> verificationEmail(@RequestParam("email") @Valid String email,
                                               @RequestParam("verifyCode") @Valid String verifyCode){
        appUserService.verifiedCode(email, verifyCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
