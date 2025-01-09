package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class QnaList {

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

    @Builder
    public QnaList(String language, String title, String content, String nickname, Long viewCount, Long answerCount, LocalDateTime createDate) {
        this.language = language;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.createDate = createDate;
    }

    public static QnaList from(Board board) {
        return QnaList.builder()
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
