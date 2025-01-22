package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeRoomCreateResponse {
    private Long id;
    private String uuid;

    public static CodeRoomCreateResponse from(CodeRoom codeRoom) {
        return CodeRoomCreateResponse.builder()
                .id(codeRoom.getId())
                .uuid(codeRoom.getUuid())
                .build();
    }
}
