package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeRoomEnterResponse {
    private Long id;
    private String language;

    public static CodeRoomEnterResponse from(CodeRoom codeRoom) {
        return CodeRoomEnterResponse.builder()
                .id(codeRoom.getId())
                .language(codeRoom.getLanguage())
                .build();
    }
}
