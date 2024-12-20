package com.gagoo.thiscoding.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    JOIN_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다"),
    PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    CLAIM_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰에 필요한 클레임이 존재하지 않습니다."),
    ALREADY_CREATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    ALREADY_FRIEND(HttpStatus.CONFLICT, "이미 친구목록에 존재하는 회원입니다."),
    ALREADY_USER_CODE_ROOM(HttpStatus.CONFLICT, "이미 참여중인 코드방입니다."),
    EXIST_MEMBER_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    OVER_CAPACITY_CODE_ROOM(HttpStatus.CONFLICT, "인원 수가 초과되었습니다."),
    USER_CODE_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코드방입니다."),
    CODE_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코드방입니다."),
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다."),
    JOIN_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증코드가 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND,"친구를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없거나 형식이 잘못되었습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다"),
    TOKEN_NOT_EQUALS(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다"),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인하지 않은 사용자는 접근할 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}
