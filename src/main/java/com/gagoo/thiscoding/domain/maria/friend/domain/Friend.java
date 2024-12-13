package com.gagoo.thiscoding.domain.maria.friend.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Friend {

    private Long id;
    private User sender;
    private User receiver;
    private boolean isFriend;

    @Builder
    public Friend(Long id, User sender, User receiver, boolean isFriend) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.isFriend = isFriend;
    }

    public static Friend from(User sender, User receiver){
        return Friend.builder()
            .sender(sender)
            .receiver(receiver)
            .build();
    }
}
