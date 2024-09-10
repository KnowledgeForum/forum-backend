package com.project.forum.board;

import com.project.forum.controller.BoardController;
import com.project.forum.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> generateData() {
        return Stream.of(
            Arguments.arguments("N", "title", "content", List.of(1L, 2L, 3L), 400), // 사용자 없음 또는 태그 없음
            Arguments.arguments("N", null, "content", List.of(1L), 400), // 제목 없음
            Arguments.arguments("N", "title", null, List.of(1L), 400), // 내용 없음
            Arguments.arguments("N", "title", "content", List.of(1L, 2L, 3L, 4L), 400), // 태그 개수 초과
            Arguments.arguments("N", "title", "content", null, 400), // 태그 없음
            Arguments.arguments("C", "title", "content", List.of(1L, 2L, 3L), 400) // 게시판 유형이 없음
        );
    }

    @DisplayName("게시판 생성 실패")
    @ParameterizedTest
    @MethodSource("generateData")
    void createBoardFailed(String boardType, String title, String content, List<Long> tagIds, int expectedCode) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.POST, "/board")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("boardType", boardType)
                .param("title", title)
                .param("content", content);

        if (tagIds != null) {
            for (Long tagId : tagIds) {
                requestBuilder.param("tagIds", tagId.toString());
            }
        }

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(expectedCode));

        log.debug("Request Payload: {}", boardType + " " + title + " " + content + " " + tagIds);
    }

    @Test
    @DisplayName("게시판 생성 성공")
    @Rollback
    void createBoardSuccess() throws Exception {
        mockMvc.perform(
            multipart(HttpMethod.POST, "/board")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("boardType", "N")
                .param("title", "title2")
                .param("content", "content2")
                .param("tagIds", "1")
        ).andExpect(status().isCreated());
    }
}