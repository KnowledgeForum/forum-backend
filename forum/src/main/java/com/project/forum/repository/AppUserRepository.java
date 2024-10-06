package com.project.forum.repository;

import com.project.forum.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserId(Long userId);

    AppUser save(AppUser appUser); //회원가입

    Optional<AppUser> findByEmail(String email); //중복 가입 확인
}
