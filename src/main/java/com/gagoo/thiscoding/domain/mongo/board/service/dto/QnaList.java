package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaList {

    @JsonProperty
    private final String qnaId;
    @JsonProperty
    private final String language;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String content;
    @JsonProperty
    private final String nickname;
    @JsonProperty
    private final Long viewCount;
    @JsonProperty
    private final Long answerCount;
    @JsonProperty
    private final LocalDateTime createDate;

    public static QnaList from(Board board) {
        return QnaList.builder()
                .qnaId(board.getId())
                .language(board.getLanguage())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .viewCount(board.getViewCount())
                .answerCount(board.getAnswerCount())
                .createDate(board.getCreateDate())
                .build();
    }
}
