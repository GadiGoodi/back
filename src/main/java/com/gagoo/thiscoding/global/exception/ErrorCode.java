package com.gagoo.thiscoding.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SOCIAL_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "이메일이 제공되지 않는 소셜 로그인은 허용되지 않습니다."),
    AUTH_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다"),
    PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    CLAIM_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰에 필요한 클레임이 존재하지 않습니다."),
    INVALID_PAGE_INDEX(HttpStatus.BAD_REQUEST, "페이지가 유효하지 않습니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "페이지가 유효하지 않습니다."),
    SOCIAL_ACCOUNT_CONFLICT(HttpStatus.CONFLICT, "이미 해당 이메일로 가입된 아이디가 있습니다."),
    ALREADY_CREATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    ALREADY_CODE_ROOM(HttpStatus.CONFLICT, "이미 참여중인 코드방입니다."),
    ALREADY_FRIEND(HttpStatus.CONFLICT, "이미 친구목록에 존재하는 회원입니다."),
    ALREADY_USER_CODE_ROOM(HttpStatus.CONFLICT, "이미 참여중인 코드방입니다."),
    ALREADY_LIKE(HttpStatus.CONFLICT, "이미 추천한 답변입니다."),
    ALREADY_BOOKMARK(HttpStatus.CONFLICT, "이미 북마크한 게시글입니다."),
    EXIST_MEMBER_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    EXIST_CODE_FILENAME(HttpStatus.CONFLICT, "이미 존재하는 파일명입니다."),
    CAPACITY_CODE_ROOM(HttpStatus.CONFLICT, "코드방이 유효하지 않습니다."),
    USER_CODE_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코드방입니다."),
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "알람을 찾을 수 없습니다."),
    CODE_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코드방입니다."),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 파일입니다."),
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다."),
    AUTH_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증코드가 존재하지 않습니다."),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND,"친구를 찾을 수 없습니다."),
    QNA_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 추천입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."),
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED,"OAuth2 인증에서 예상치 못한 오류가 발생했습니다."),
    NOT_REPLY_AUTHOR(HttpStatus.UNAUTHORIZED, "댓글 작성자가 아닙니다."),
    NOT_USER_CODE_ROOM_PARTICIPANT(HttpStatus.UNAUTHORIZED, "참여하지 않은 코드방입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없거나 형식이 잘못되었습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다"),
    TOKEN_NOT_EQUALS(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다"),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인하지 않은 사용자는 접근할 수 없습니다."),
    NOTICES_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 공지사항입니다.");

    private final HttpStatus status;
    private final String message;

}
