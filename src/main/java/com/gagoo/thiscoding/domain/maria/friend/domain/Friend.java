package com.gagoo.thiscoding.domain.maria.friend.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

@Getter
public class Friend {

    private Long id;
    private User sender;
    private User receiver;
    private boolean isFriend;
}
