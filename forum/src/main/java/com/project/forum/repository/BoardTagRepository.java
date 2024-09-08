package com.project.forum.repository;

import com.project.forum.entity.Board;
import com.project.forum.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    List<BoardTag> findAllByBoard(Board board);
}
