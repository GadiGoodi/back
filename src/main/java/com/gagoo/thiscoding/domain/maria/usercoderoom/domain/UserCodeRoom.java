package com.gagoo.thiscoding.domain.maria.usercoderoom.domain;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.UserCodeRoomCreate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCodeRoom {
    private final Long id;
    private final User user;
    private final CodeRoom codeRoom;
    private final boolean isActivated;
    private final boolean isAccepted;

    @Builder
    public UserCodeRoom(Long id, User user, CodeRoom codeRoom, boolean isActivated, boolean isAccepted) {
        this.id = id;
        this.user = user;
        this.codeRoom = codeRoom;
        this.isActivated = isActivated;
        this.isAccepted = isAccepted;
    }

    public UserCodeRoom accept() {
        return UserCodeRoom.builder()
                .id(id)
                .user(user)
                .codeRoom(codeRoom)
                .isAccepted(true)
                .isActivated(true)
                .build();
    }

    public static UserCodeRoom create(User user, CodeRoom codeRoom) {
        return UserCodeRoom.builder()
                .user(user)
                .codeRoom(codeRoom)
                .isActivated(false)
                .isAccepted(true)
                .build();
    }

}
