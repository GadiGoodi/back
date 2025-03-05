package com.gagoo.thiscoding.domain.maria.usercoderoom.domain;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCodeRoom {
    private final Long id;
    private final User user;
    private final CodeRoom codeRoom;
    private final boolean isActivated;

    @Builder
    public UserCodeRoom(Long id, User user, CodeRoom codeRoom, boolean isActivated) {
        this.id = id;
        this.user = user;
        this.codeRoom = codeRoom;
        this.isActivated = isActivated;
    }

    public UserCodeRoom accept() {
        return UserCodeRoom.builder()
                .id(id)
                .user(user)
                .codeRoom(codeRoom)
                .isActivated(true)
                .build();
    }

    public static UserCodeRoom create(User user, CodeRoom codeRoom) {
        return UserCodeRoom.builder()
                .user(user)
                .codeRoom(codeRoom)
                .isActivated(false)
                .build();
    }

    public UserCodeRoom access() {
        return UserCodeRoom.builder()
                .id(id)
                .user(user)
                .codeRoom(codeRoom)
                .isActivated(!isActivated)
                .build();
    }
}
