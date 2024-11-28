package com.gagoo.thiscoding.domain.maria.coderoom.domain;

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
}
