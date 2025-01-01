package com.gagoo.thiscoding.domain.mongo.answer.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AnswerCreate {

    private final String boardId;
    private final String content;

    @Builder
    public AnswerCreate(String boardId, String content) {
        this.boardId = boardId;
        this.content = content;
    }
}
