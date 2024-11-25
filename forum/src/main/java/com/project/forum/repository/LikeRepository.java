package com.project.forum.repository;

import com.project.forum.entity.AppUser;
import com.project.forum.entity.Board;
import com.project.forum.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserAndBoard(AppUser user, Board board);
}
