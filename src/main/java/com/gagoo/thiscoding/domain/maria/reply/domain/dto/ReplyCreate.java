package com.gagoo.thiscoding.domain.maria.reply.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyCreate {

    @NotBlank
    private final String content;
    private final Long parentId;

    @Builder
    public ReplyCreate(String content, Long parentId) {
        this.content = content;
        this.parentId = parentId;
    }
}
