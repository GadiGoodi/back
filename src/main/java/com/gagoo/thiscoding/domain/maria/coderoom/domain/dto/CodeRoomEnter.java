package com.gagoo.thiscoding.domain.maria.coderoom.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeRoomEnter {
    @NotBlank
    private final Long roomId;
    @NotBlank
    private final String language;
    @NotBlank
    private final String value;
    @NotBlank
    private final String codeId;

    @Builder
    public CodeRoomEnter(Long roomId, String language, String value, String codeId) {
        this.roomId = roomId;
        this.language = language;
        this.value = value;
        this.codeId = codeId;
    }

    public static CodeRoomEnter enterCodeRoom(Long roomId, String language, String value, String codeId) {
        return CodeRoomEnter.builder()
                .roomId(roomId)
                .language(language)
                .value(value)
                .codeId(codeId)
                .build();
    }

}
