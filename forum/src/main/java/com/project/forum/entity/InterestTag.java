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
@Table(name = "interest_tag")
public class InterestTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_tag_id", nullable = false, unique = true, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long interestTagId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Tag tag; //ON UPDATE CASCADE 설정 해줘야함

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private AppUser appUser; //ON UPDATE CASCADE 설정 해줘야함

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime; //생성된 시간
}
