package com.gagoo.thiscoding.domain.maria.coderoom.domain;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CodeRoom {

    private Long id;
    private UUID uuid;
    private String title;
    private String content;
    private String language;
    private int headCount;

    @Builder
    public CodeRoom(Long id, UUID uuid, String title, String content, String language,
                    int headCount) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.language = language;
        this.headCount = headCount;
    }

    public static CodeRoom create(CodeRoomCreate codeRoomCreate) {
        return CodeRoom.builder()
                .uuid(UUID.randomUUID())
                .title(codeRoomCreate.getTitle())
                .content(codeRoomCreate.getContent())
                .language(codeRoomCreate.getLanguage())
                .headCount(1)
                .build();
    }
}
