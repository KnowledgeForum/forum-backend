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
@Table(name = "sns")
public class Sns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long snsId; // snsID

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser appUser; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    @Column(name = "sns_kind", nullable = false, columnDefinition = "CHAR", length = 4)
    private String snsKind;
    // 1001 : 카카오톡
    // 1002 : 네이버
    // 1003 : 구글
    // 1004 : 깃헙

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime; //생성된 시간
}
