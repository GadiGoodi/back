package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import java.time.LocalDateTime;

public record QnaList(String qnaId, String language, String title, String content,  String nickname, Long viewCount, Long answerCount, LocalDateTime createDate) {

    public static QnaList from(Board board) {
        return new QnaList(
                board.getId(),
                board.getLanguage(),
                board.getTitle(),
                board.getContent(),
                board.getNickname(),
                board.getViewCount(),
                board.getAnswerCount(),
                board.getCreateDate()
        );
    }
}
