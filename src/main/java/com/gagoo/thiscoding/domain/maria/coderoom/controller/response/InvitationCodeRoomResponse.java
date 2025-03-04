package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitationCodeRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvitationCodeRoomResponse {
    private final Long alarmId;
    private final Long codeRoomId;
    private final String title;
    private final String content;
    private final String language;
    private final int headCount;
    private final String nickname;
    private final String imageUrl;

    public static InvitationCodeRoomResponse from(InvitationCodeRoom InvitationCodeRoom) {
        return InvitationCodeRoomResponse.builder()
            .alarmId(InvitationCodeRoom.getAlarmId())
            .codeRoomId(InvitationCodeRoom.getCodeRoomId())
            .title(InvitationCodeRoom.getTitle())
            .content(InvitationCodeRoom.getContent())
            .headCount(InvitationCodeRoom.getHeadCount())
            .language(InvitationCodeRoom.getLanguage())
            .nickname(InvitationCodeRoom.getNickname())
            .imageUrl(InvitationCodeRoom.getImageUrl())
            .build();
    }
}
