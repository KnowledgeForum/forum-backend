
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
@Table(name = "board_image")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long imageId; //

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private Board board; //=> ON UPDATE CASCADE 이 옵션은 mysql에서 직접 설정하기

    @Column(name = "image_size", nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer imageSize;

    @Column(name = "image_type", nullable = false, columnDefinition = "VARCHAR", length = 20)
    private String imageType;

    @Column(name = "image_path", nullable = false, columnDefinition = "VARCHAR", length = 200)
    private String imagePath;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTime; //생성된 시간

}
