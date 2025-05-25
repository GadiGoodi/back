package com.gagoo.thiscoding.domain.mongo.board.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KeywordSearchResult {
    private String qnaId;
    private String language;
    private String title;
    private String content;
    private String nickname;
    private Long viewCount;
    private Long answerCount;
    private Boolean isAdopted;
    private LocalDateTime createDate;
}
