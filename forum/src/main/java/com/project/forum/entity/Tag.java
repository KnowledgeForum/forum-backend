package com.project.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
//@Setter
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false, unique = true, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long tagId;

    @Column(name = "tag_name", nullable = false, unique = true, length = 10)
    private String tagName;

    @Column(name = "tag_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer tagCount; //인증코드

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime; //유효시간 => 5분 이내로 계산
}
