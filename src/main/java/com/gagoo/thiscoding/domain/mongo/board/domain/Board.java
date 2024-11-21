package com.gagoo.thiscoding.domain.mongo.board.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Board {
    private String id;
    private Long userId;
    private String title;
    private String content;
    private String language;
    private Long parentId;
    private Long likeCount;
    private Long viewCount;
    private Long answerCount;
    private boolean isBlind;
    private boolean isSelected;
    private LocalDateTime createDate;
}
