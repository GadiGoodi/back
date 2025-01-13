package com.gagoo.thiscoding.domain.maria.coderoom.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class InvitedCodeRoom {

    private final Long alarmId;
    private final Long codeRoomId;
    private final String title;
    private final String content;
    private final int headCount;
    private final String language;
    private final String nickname;
    private final String imageUrl;

    @Builder
    public InvitedCodeRoom(Long alarmId, Long codeRoomId, String title, String content,
        int headCount, String language, String nickname, String imageUrl) {
        this.alarmId = alarmId;
        this.codeRoomId = codeRoomId;
        this.title = title;
        this.content = content;
        this.headCount = headCount;
        this.language = language;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
