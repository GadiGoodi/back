package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeRoomEnterResponse {
    private Long roomId;
    private String language;
    private String value;
    private String codeId;

    public static CodeRoomEnterResponse from(CodeRoomEnter codeRoomEnter) {
        return CodeRoomEnterResponse.builder()
                .roomId(codeRoomEnter.getRoomId())
                .language(codeRoomEnter.getLanguage())
                .value(codeRoomEnter.getValue())
                .codeId(codeRoomEnter.getCodeId())
                .build();
    }
}
