package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvitedCodeRoomResponse {
    private final Long alarmId;
    private final Long codeRoomId;
    private final String title;
    private final String content;
    private final String language;
    private final int headCount;
    private final String nickname;
    private final String imageUrl;

    public static InvitedCodeRoomResponse from(InvitedCodeRoom InvitedCodeRoom) {
        return InvitedCodeRoomResponse.builder()
            .alarmId(InvitedCodeRoom.getAlarmId())
            .codeRoomId(InvitedCodeRoom.getCodeRoomId())
            .title(InvitedCodeRoom.getTitle())
            .content(InvitedCodeRoom.getContent())
            .headCount(InvitedCodeRoom.getHeadCount())
            .language(InvitedCodeRoom.getLanguage())
            .nickname(InvitedCodeRoom.getNickname())
            .imageUrl(InvitedCodeRoom.getImageUrl())
            .build();
    }
}
