package com.project.forum.repository;

import com.project.forum.entity.DummyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DummyImageRepository extends JpaRepository<DummyImage, Long> {
    List<DummyImage> findAllByImageIdIn(List<Long> imageIds);
}
