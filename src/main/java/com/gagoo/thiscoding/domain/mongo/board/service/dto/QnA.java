package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;

import java.time.LocalDateTime;

public record QnA(
        @JsonProperty("qnaId") String qnaId,
        @JsonProperty("language") String language,
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("viewCount") Long viewCount,
        @JsonProperty("answerCount") Long answerCount,
        @JsonProperty("isAdopted") Boolean isAdopted,
        @JsonProperty("createDate") LocalDateTime createDate
) {

    public static QnA from(Board board, BoardStats stats) {
        return new QnA(
                board.getId(),
                board.getLanguage(),
                board.getTitle(),
                board.getContent(),
                board.getNickname(),
                stats.getViewCount(),
                stats.getAnswerCount(),
                board.isAdopted(),
                board.getCreateDate()
        );
    }
}
