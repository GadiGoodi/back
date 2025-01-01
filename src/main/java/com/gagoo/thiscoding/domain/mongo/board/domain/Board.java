package com.gagoo.thiscoding.domain.mongo.board.domain;

import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
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
    private Long viewCount;
    private Long answerCount;
    private boolean isBlind;
    private LocalDateTime createDate;

    @Builder
    public Board(String id, Long userId,String title,String content,String language,Long viewCount
    ,Long answerCount,Boolean isBlind,LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.language = language;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.isBlind = isBlind;
        this.createDate = createDate;
    }

    public static Board create(BoardCreate boardCreate) {
        return Board.builder()
                .userId(boardCreate.getUserId())
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .language(boardCreate.getLanguage())
                .viewCount(0L)
                .answerCount(0L)
                .isBlind(false)
                .build();
    }

    public static Board from(BoardDocument boardDocument) {
        return Board.builder().build();
    }
}
