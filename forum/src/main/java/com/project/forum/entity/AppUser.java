package com.project.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "app_user")
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long userId;

    @Column(name = "user_pw", nullable = false, length = 300)
    private String userPw;

    @Column(name = "email", nullable = false, unique = true, length = 60)
    private String email;

    @Column(name = "profile_path", length = 200)
    private String profilePath;

    @Column(name = "profile_size", length = 20)
    private String profileSize;

    @Column(name = "profile_type", length = 20)
    private String profileType;

    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "description", length = 40)
    private String description;

    @Column(name = "board_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer boardCount;

    @Column(name = "news_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer newsCount;

    @Column(name = "comment_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer commentCount;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer likeCount;

    @Column(name = "follower_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer followerCount;

    @Column(name = "following_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer followingCount;

    @Column(name = "disable_follow", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean disableFollow;

    @Column(name = "private_account", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean privateAccount;

    @Column(name = "notice_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean noticeNotification;

    @Column(name = "comment_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean commentNotification;

    @Column(name = "like_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean likeNotification;

    @Column(name = "recommend_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean recommendNotification;

    @Column(name = "follow_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean followNotification;

    @Column(name = "event_notification", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean eventNotification;

    @Column(name = "deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Column(name = "deleted_time")
    private Timestamp deletedTime;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;
}
