package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

import java.time.LocalDateTime;

public record AnswerList(String qnaId, String nickname, String profileImage, String content, Long likeCount, Long answerCount, LocalDateTime createDate) {

    public static AnswerList from(Board board) {
        return new AnswerList(
                board.getId(),
                board.getNickname(),
                board.getProfileImg(),
                board.getContent(),
                board.getLikeCount(),
                board.getAnswerCount(),
                board.getCreateDate()
        );
    }
}

