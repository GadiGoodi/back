package com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InviteCodeRoom {
    private final Long codeRoomId;
    private final String title;
    private final String content;
    private final String language;
    private final String nickname;
    private final int headCount;

    @Builder
    public static InviteCodeRoom from(UserCodeRoom userCodeRoom){
        CodeRoom codeRoom = userCodeRoom.getCodeRoom();
        User user = userCodeRoom.getUser();

        return InviteCodeRoom.builder()
            .codeRoomId(codeRoom.getId())
            .title(codeRoom.getTitle())
            .content(codeRoom.getContent())
            .language(codeRoom.getLanguage())
            .nickname(user.getNickname())
            .headCount(codeRoom.getHeadCount())
            .build();
    }
}
