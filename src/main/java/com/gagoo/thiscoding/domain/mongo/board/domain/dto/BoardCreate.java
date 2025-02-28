package com.gagoo.thiscoding.domain.mongo.board.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardCreate(@NotBlank String title, @NotBlank String content, @NotBlank String language) {
    public static BoardCreate of(String title, String content, String language) {
        return new BoardCreate(title, content, language);
    }
}
