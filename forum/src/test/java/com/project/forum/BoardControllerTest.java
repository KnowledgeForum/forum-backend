package com.project.forum;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Stream;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BoardControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    static Stream<Arguments> generateData() {
        return Stream.of(
            Arguments.arguments("N", "title", "content", "1,2,3", 404), // 태그 없음
            Arguments.arguments("N", null, "content", "1", 400), // 제목 없음
            Arguments.arguments("N", "title", null, "1", 400), // 내용 없음
            Arguments.arguments("N", "title", "content", "1,2,3,4", 400), // 태그 개수 초과
            Arguments.arguments("N", "title", "content", null, 400), // 태그 없음
            Arguments.arguments("C", "title", "content", "1,2,3", 400) // 게시판 유형이 없음
        );
    }

    @DisplayName("게시판 생성 및 필수 값 검증")
    @ParameterizedTest
    @MethodSource("generateData")
    @Rollback
    void 보드_생성(String boardType, String title, String content, String tagIds, int expectedCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("boardType", boardType);
        body.add("title", title);
        body.add("content", content);
        body.add("tagIds", tagIds);

        log.debug("Request Payload: {}", body);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<?> response = testRestTemplate.postForEntity("/board", request, Object.class);
        log.debug("Response Body: {}", response.getBody());

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(expectedCode);
    }
}