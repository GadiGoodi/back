package com.gagoo.thiscoding.domain.maria.reply.domain.dto;

import com.gagoo.thiscoding.domain.maria.reply.infrastructure.ReplyEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyCreate {

    @NotBlank
    private final String content;
    private final Long parent;

    @Builder
    public ReplyCreate(String content, Long parent) {
        this.content = content;
        this.parent = parent;
    }
}
