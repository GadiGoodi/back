package com.gagoo.thiscoding.domain.mongo.board.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreate {

    @NotBlank private final Long userId;
    @NotBlank private final String title;
    @NotBlank private final String content;
    @NotBlank private final String language;
    private final Long parentId;

    @Builder
    public BoardCreate(Long userId, String title, String content, String language, Long parentId) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.language = language;
        this.parentId = parentId;
    }
}
