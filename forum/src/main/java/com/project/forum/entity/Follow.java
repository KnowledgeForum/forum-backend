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
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long followId; //

    // 보낸사람 (팔로워)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", referencedColumnName = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser from; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    // 받는 사람 (팔로잉)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", referencedColumnName = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser to; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime; //생성된 시간
}
