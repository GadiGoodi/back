package com.gagoo.thiscoding.domain.mongo.code.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeCreate {
    @NotBlank
    private final Long roomId;
    @NotBlank
    private final Long writerId;
    @NotBlank
    private final String fileName;
    @NotBlank
    private final String value;

    @Builder
    public CodeCreate(Long roomId, Long writerId, String fileName, String value) {
        this.roomId = roomId;
        this.writerId = writerId;
        this.fileName = fileName;
        this.value = value;
    }
}
