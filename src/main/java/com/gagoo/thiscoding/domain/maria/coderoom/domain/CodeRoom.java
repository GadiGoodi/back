package com.gagoo.thiscoding.domain.maria.coderoom.domain;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeRoom {

    private Long id;
    private String uuid;
    private String title;
    private String content;
    private String language;
    private int headCount;

    @Builder
    public CodeRoom(Long id, String uuid, String title, String content, String language, int headCount) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.language = language;
        this.headCount = headCount;
    }

    public static CodeRoom create(CodeRoomCreate codeRoomCreate, UuidHolder uuidHolder) {
        return CodeRoom.builder()
                .uuid(uuidHolder.random())
                .title(codeRoomCreate.getTitle())
                .content(codeRoomCreate.getContent())
                .language(codeRoomCreate.getLanguage())
                .headCount(1)
                .build();
    }

    public CodeRoom join() {
        return CodeRoom.builder()
            .id(id)
            .uuid(uuid)
            .title(title)
            .content(content)
            .language(language)
            .headCount(++headCount)
            .build();
    }

    public CodeRoom exit() {
        return CodeRoom.builder()
                .id(id)
                .uuid(uuid)
                .title(title)
                .content(content)
                .language(language)
                .headCount(--headCount)
                .build();
    }

}
