package com.gagoo.thiscoding.domain.mongo.board.controller.response;

import java.time.LocalDateTime;

import com.gagoo.thiscoding.domain.mongo.board.service.dto.KeywordSearchResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordSearchResponse {
    private String id;
    private String language;
    private String title;
    private String content;
    private String nickname;
    private Long viewCount;
    private Long answerCount;
    private boolean isAdopted;
    private LocalDateTime createDate;

    public static KeywordSearchResponse from(KeywordSearchResult result) {
        return KeywordSearchResponse.builder()
            .id(result.getQnaId())
            .nickname(result.getNickname())
            .title(result.getTitle())
            .content(result.getContent())
            .language(result.getLanguage())
            .viewCount(result.getViewCount())
            .answerCount(result.getAnswerCount())
            .isAdopted(result.getIsAdopted())
            .createDate(result.getCreateDate())
            .build();
    }
}
