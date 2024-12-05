package com.gagoo.thiscoding.domain.maria.coderoom.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeRoomCreate {
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;
    @NotBlank
    private final String language;

    @Builder
    public CodeRoomCreate(String title, String content, String language) {
        this.title = title;
        this.content = content;
        this.language = language;
    }
}
