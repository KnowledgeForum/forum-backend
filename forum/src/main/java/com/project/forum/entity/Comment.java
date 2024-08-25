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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long commentId; //

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", columnDefinition = "INT UNSIGNED")
    private AppUser parent;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser uploader; // ON UPDATE CASCADE AND ON DELETE SET NULL 둘 다 설정 해줘야함

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Board board; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; //댓글 내용

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTime; //생성된 시간

    @CreationTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime; //업데이트 시간

}
