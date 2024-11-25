package com.project.forum.repository;

import com.project.forum.entity.AppUser;
import com.project.forum.entity.Board;
import com.project.forum.entity.BoardView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewRepository extends JpaRepository<BoardView, Long> {
    Optional<BoardView> findByUserAndBoard(AppUser user, Board board);
}
