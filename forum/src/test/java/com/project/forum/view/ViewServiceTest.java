package com.project.forum.view;

import com.project.forum.AbstractTest;
import com.project.forum.entity.BoardView;
import com.project.forum.mapper.ViewMapper;
import com.project.forum.repository.AppUserRepository;
import com.project.forum.repository.BoardRepository;
import com.project.forum.repository.ViewRepository;
import com.project.forum.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@Slf4j
@ExtendWith({ MockitoExtension.class })
public class ViewServiceTest extends AbstractTest {
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private ViewRepository viewRepository;
    @InjectMocks
    private ViewService viewService;

    @Test
    @DisplayName("게시글 조회수 증가")
    void increaseViewCount() {
        Long userId = 1L;
        Long boardId = 1L;

        Mockito.when(appUserRepository.findByUserId(userId)).thenReturn(Optional.of(super.getAppUser()));
        Mockito.when(boardRepository.findById(boardId)).thenReturn(Optional.of(super.getBoard()));
        Mockito.when(viewRepository.findByUserAndBoard(super.getAppUser(), super.getBoard())).thenReturn(Optional.empty());

        // then
        viewService.createView(userId, boardId);

        Mockito.verify(boardRepository, Mockito.times(1)).save(super.getBoard());
        Mockito.verify(viewRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
