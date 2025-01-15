package com.gagoo.thiscoding.domain.maria.coderoom.controller.response;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ParticipatingCodeRoomResponse {
    private Long userCodeRoomId;
    private Long codeRoomId;
    private String title;
    private String content;
    private String language;
    private List<User> userList;
    private int headCount;

    @Builder
    public static ParticipatingCodeRoomResponse from(UserCodeRoom userCodeRoom, List<User> userList) {
        CodeRoom codeRoom = userCodeRoom.getCodeRoom();

        return ParticipatingCodeRoomResponse.builder()
                .userCodeRoomId(userCodeRoom.getId())
                .codeRoomId(codeRoom.getId())
                .title(codeRoom.getTitle())
                .content(codeRoom.getContent())
                .language(codeRoom.getLanguage())
                .userList(userList)
                .headCount(codeRoom.getHeadCount())
                .build();
    }

}
