//package com.project.forum.service;
//
//import com.project.forum.dto.ResponseOtherUserDto;
//import com.project.forum.entity.AppUser;
//import com.project.forum.exception.CustomException;
//import com.project.forum.exception.ErrorCode;
//import com.project.forum.mapper.AppUserMapper;
//import com.project.forum.repository.AppUserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AppUserService {
//    private final AppUserRepository appUserRepository;
//
//    public ResponseOtherUserDto getOtherUser(Long userId) {
//        AppUser user = appUserRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
//
//        return AppUserMapper.INSTANCE.toResponseOtherUserDto(user);
//    }
//}
