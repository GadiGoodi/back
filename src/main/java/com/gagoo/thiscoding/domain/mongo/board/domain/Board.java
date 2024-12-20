package com.gagoo.thiscoding.domain.mongo.board.domain;

import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import lombok.Builder;
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

    @Builder
    public Board(String id, Long userId,String title,String content,String language,Long parentId,Long likeCount,Long viewCount
    ,Long answerCount,Boolean isBlind,Boolean isSelected,LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.language = language;
        this.parentId = parentId;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.isBlind = isBlind;
        this.isSelected = isSelected;
        this.createDate = createDate;
    }

    public static Board create(BoardCreate boardCreate) {
        return Board.builder()
                .userId(boardCreate.getUserId())
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .language(boardCreate.getLanguage())
                .parentId(boardCreate.getParentId())
                .likeCount(0L)
                .viewCount(0L)
                .answerCount(0L)
                .isBlind(false)
                .isSelected(false)
                .build();
    }

}
