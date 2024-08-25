
package com.project.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
//@Setter
@Entity
@Table(name = "bummy_image")
public class DummyImage { // 진짜 게시글을 올릴 시 boardImage에 bummyImgae 데이터 올리길
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long imageId; //

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
