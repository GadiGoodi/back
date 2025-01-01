package com.gagoo.thiscoding.domain.mongo.answer.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Answer {

    private final String id;

    private final Long userId;

    private final String content;

    private final Long likeCount;

    private final boolean isBlind;

    private final boolean isSelected;

    private final LocalDateTime createDate;

    @Builder
    public Answer(String id, Long userId, String content, Long likeCount, boolean isBlind, boolean isSelected, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likeCount = likeCount;
        this.isBlind = isBlind;
        this.isSelected = isSelected;
        this.createDate = createDate;
    }
}
