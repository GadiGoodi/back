package com.gagoo.thiscoding.domain.maria.usercoderoom.domain;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class UserCodeRoom {
    private Long id;
    private User user;
    private CodeRoom codeRoom;
    private boolean isActivated;
    private boolean isAccepted;
}
