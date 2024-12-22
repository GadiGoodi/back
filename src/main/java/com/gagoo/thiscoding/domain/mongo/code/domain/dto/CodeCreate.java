package com.gagoo.thiscoding.domain.mongo.code.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeCreate {
    private final String id;
    @NotBlank
    private final Long roomId;
    @NotBlank
    private final Long writerId;
    @NotBlank
    private final String value;
    @NotBlank
    private final String fileName;

    @Builder
    public CodeCreate(String id, Long roomId, Long writerId, String fileName, String value) {
        this.id = id;
        this.roomId = roomId;
        this.writerId = writerId;
        this.value = value;
        this.fileName = fileName;
    }
}
