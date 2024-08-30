package com.project.forum.repository;

import com.project.forum.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserId(Long userId);

    AppUser save(AppUser appUser);

    Optional<AppUser> findByEmail(String email);
}
