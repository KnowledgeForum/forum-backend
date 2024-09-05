package com.project.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // ErrorCode의 메시지를 RuntimeException의 메시지로 설정
        this.errorCode = errorCode;
    }

    public CustomException() {

    }
}
