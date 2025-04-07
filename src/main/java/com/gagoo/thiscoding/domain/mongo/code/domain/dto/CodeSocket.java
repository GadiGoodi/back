package com.gagoo.thiscoding.domain.mongo.code.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeSocket {
    @NotBlank
    private final Long roomId;
    @NotBlank
    private final String codeId;
    @NotBlank
    private final String value;

    @Builder
    public CodeSocket(final Long roomId, final String codeId, final String value) {
        this.roomId = roomId;
        this.codeId = codeId;
        this.value = value;
    }
}
