package com.project.forum.repository;

import com.project.forum.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByTagIdIn(List<Long> tagIds);
}
