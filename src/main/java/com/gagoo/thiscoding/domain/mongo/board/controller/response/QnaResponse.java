package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaResponse {

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
    private final LocalDateTime createDate;

    public static QnaResponse from(QnaDetail qnaDetail) {
        return QnaResponse.builder()
                .qnaId(qnaDetail.getQnaId())
                .language(qnaDetail.getLanguage())
                .title(qnaDetail.getTitle())
                .content(qnaDetail.getContent())
                .nickname(qnaDetail.getNickname())
                .viewCount(qnaDetail.getViewCount())
                .answerCount(qnaDetail.getAnswerCount())
                .replyCount(qnaDetail.getReplyCount())
                .createDate(qnaDetail.getCreateDate())
                .build();
    }
}
