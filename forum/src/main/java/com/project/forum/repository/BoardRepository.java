package com.project.forum.repository;

import com.project.forum.entity.Board;
import com.project.forum.type.BoardTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByOrderByCreatedTimeDesc(Pageable pageable);
    Page<Board> findAllByCategoryOrderByCreatedTimeDesc(Pageable pageable, Character type);
    Page<Board> findALlByTitleContainingOrderByCreatedTimeDesc(Pageable pageable, String keyword);
}
