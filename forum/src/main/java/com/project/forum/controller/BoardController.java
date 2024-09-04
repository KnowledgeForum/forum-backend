package com.project.forum.controller;

import com.project.forum.dto.board.BoardDto;
import com.project.forum.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@ResponseBody
public class BoardController {
    private final BoardService boardService;

    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBoard(@Valid @ModelAttribute final BoardDto.Request request) {
        Long boardId = boardService.create(1L, request);

        return ResponseEntity.created(URI.create("/" + boardId)).build();
    }
}
