package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import java.time.LocalDateTime;

public record MyPageBookMarkList(String qnaId, String title, String content, String nickname, String language,
                                 Long viewCount, Long answerCount, LocalDateTime createDate) {

    public static MyPageBookMarkList of(Board board) {
        return new MyPageBookMarkList(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getNickname(),
                board.getLanguage(),
                board.getViewCount(),
                board.getAnswerCount(),
                board.getCreateDate()
        );
    }
}
