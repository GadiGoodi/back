package com.gagoo.thiscoding.domain.mongo.board.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreate {

    @NotBlank private final String title;
    @NotBlank private final String content;
    @NotBlank private final String language;
    private final String parentId;

    @Builder
    public BoardCreate(String title, String content, String language, String parentId) {
        this.title = title;
        this.content = content;
        this.language = language;
        this.parentId = parentId;
    }
}
