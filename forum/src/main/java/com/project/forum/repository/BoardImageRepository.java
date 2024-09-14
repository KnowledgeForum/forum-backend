package com.project.forum.repository;

import com.project.forum.entity.Board;
import com.project.forum.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findAllByBoard(Board board);
}
