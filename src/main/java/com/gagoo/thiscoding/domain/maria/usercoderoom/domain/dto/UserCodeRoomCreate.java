package com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCodeRoomCreate {
    @NotBlank
    private final User user;
    @NotBlank
    private final CodeRoom codeRoom;
    @NotBlank
    private final boolean isActivated;
    @NotBlank
    private final boolean isAccepted;

    @Builder
    public UserCodeRoomCreate(User user, CodeRoom codeRoom, boolean isActivated, boolean isAccepted) {
        this.user = user;
        this.codeRoom = codeRoom;
        this.isActivated = isActivated;
        this.isAccepted = isAccepted;
    }
}
