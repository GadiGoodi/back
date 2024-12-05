package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CodeRoomCreateResponse {
    private Long id;
    private UUID uuid;

    public static CodeRoomCreateResponse from(CodeRoom codeRoom) {
        return CodeRoomCreateResponse.builder()
                .id(codeRoom.getId())
                .uuid(codeRoom.getUuid())
                .build();
    }
}
