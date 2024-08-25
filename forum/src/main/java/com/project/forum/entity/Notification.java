
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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long notificationId; //

    @Column(name = "category", nullable = false, columnDefinition = "CHAR", length = 4)
    private String notificationType;
    // 1001:공지사항 / 1002:댓글 / 1003:좋아요 / 1004:추천뉴스|게시글 / 1005:팔로우게시글(뉴스|게시글) / 1006:이벤트

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", columnDefinition = "INT UNSIGNED")
    private AppUser sender; // ON UPDATE CASCADE AND 설정 해줘야함

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser receiver; // ON UPDATE CASCADE AND 설정 해줘야함

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", columnDefinition = "INT UNSIGNED")
    private Board board; // ON UPDATE CASCADE 설정 해줘야함

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id", columnDefinition = "INT UNSIGNED")
    private Comment comment; // ON UPDATE CASCADE 설정 해줘야함

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTime; //생성된 시간


}
