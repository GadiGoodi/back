package com.gagoo.thiscoding.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    JOIN_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다"),
    JOIN_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증코드가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

}
