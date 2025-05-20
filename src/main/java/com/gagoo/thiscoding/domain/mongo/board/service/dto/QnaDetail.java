package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaDetail {

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
    private final Long replyCount;
    @JsonProperty
    private final Boolean isAdopted;
    @JsonProperty
    private final Boolean isBookmarked;
    @JsonProperty
    private final LocalDateTime createDate;

    public static QnaDetail from(Board board, BoardStats boardStats, boolean bookmarked) {
        return QnaDetail.builder()
                .qnaId(board.getId())
                .language(board.getLanguage())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .viewCount(boardStats.getViewCount())
                .answerCount(boardStats.getAnswerCount())
                .replyCount(boardStats.getReplyCount())
                .isAdopted(board.isAdopted())
                .isBookmarked(bookmarked)
                .createDate(board.getCreateDate())
                .build();
    }
}
