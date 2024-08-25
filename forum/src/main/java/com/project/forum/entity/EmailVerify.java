package com.project.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
//@Setter
@Entity
@Table(name = "email_verify")
public class EmailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verify_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long verifyId; // 사용자ID

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser appUser; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR",length = 60)
    private String email; // 이메일

    @Column(name = "verify_code", nullable = false, columnDefinition = "CHAR", length = 6)
    private String verifyCode; //인증코드

    @Column(name = "is_verified",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVerified; //인증성공여부

    @CreationTimestamp
    @Column(name = "verify_time", nullable = false)
    private Timestamp verifyTime; //유효시간 => 5분 이내로 계산
}
