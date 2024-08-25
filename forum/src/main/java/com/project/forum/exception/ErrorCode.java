//package com.project.forum.exception;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//
//@Getter
//@AllArgsConstructor
//public enum ErrorCode {
//    EXPIRED_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "인증 시간이 만료된 이메일입니다.", "B40001"),
//    NOT_MATCHED_VERIFIED_CODE(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다.", "B40002"),
//    BROKEN_IMAGE(HttpStatus.BAD_REQUEST, "이메일이 깨져있거나 존재하지 않습니다.", "B40003"),
//    EXPIRED_RANDOM_KEY(HttpStatus.BAD_REQUEST, "변경 시간이 만료된 인증키입니다.", "B40004"),
//    NOT_FOUND_RANDOM_KEY(HttpStatus.BAD_REQUEST, "인증키가 존재하지 않습니다.", "B40005"),
//
//    NOT_MATCHED_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.", "U40101"),
//    EXPIRED_COOKIE(HttpStatus.UNAUTHORIZED, "만료된 쿠키입니다.", "U40102"),
//    BLANK_COOKIE(HttpStatus.UNAUTHORIZED, "쿠키가 존재하지 않습니다.", "U40103"),
//
//    DELETED_USER(HttpStatus.FORBIDDEN, "삭제된 사용자입니다.", "F40301"),
//    NOT_VERIFIED_USER(HttpStatus.FORBIDDEN, "이메일 인증이되지 않은 사용자입니다.", "F40302"),
//    DISABLED_FOLLOWING(HttpStatus.FORBIDDEN, "팔로잉 요청이 거부된 사용자입니다.", "F40303"),
//    PRIVATE_ACCOUNT(HttpStatus.FORBIDDEN, "비공개 계정입니다.", "F40304"),
//    NOT_MATCHED_BOARD_UPLOADER(HttpStatus.FORBIDDEN, "게시글 작성자가 아닙니다.", "F40305"),
//    NOT_MATCHED_COMMENT_UPLOADER(HttpStatus.FORBIDDEN, "댓글 작성자가 아닙니다.", "F40306"),
//
//    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.", "N40401"),
//    NOT_FOUND_VERIFIED_EMAIL(HttpStatus.NOT_FOUND, "인증을 요청한 이메일이 존재하지 않습니다.", "N40402"),
//    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.", "N40403"),
//    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.", "N40404"),
//
//    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "닉네임 중복입니다.", "C40901"),
//    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이메일 중복입니다.", "C40902"),
//    DUPLICATED_SNS_ACCOUNT(HttpStatus.CONFLICT, "SNS 계정 중복입니다.", "C40903"),
//
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생하였습니다.", "I50000"),
//    ERROR_EMAIL_SENDER(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 오류가 발생하였습니다.", "I50001"),
//    ERROR_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 오류가 발생하였습니다.", "I50002"),
//    ERROR_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 오류가 발생하였습니다.", "I50003");
//
//    private final HttpStatus status;
//    private final String message;
//    private final String errorCode;
//}
