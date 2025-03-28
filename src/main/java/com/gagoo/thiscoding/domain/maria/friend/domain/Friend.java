package com.gagoo.thiscoding.domain.maria.friend.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Friend {

    private final Long id;
    private final User sender;
    private final User receiver;
    private final boolean isFriend;

    @Builder
    public Friend(Long id, User sender, User receiver, boolean isFriend) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.isFriend = isFriend;
    }

    public static Friend create(User receiver, User sender){
        return Friend.builder()
            .receiver(receiver)
            .sender(sender)
            .build();
    }

    public static Friend from(User sender, User receiver){
        return Friend.builder()
            .sender(sender)
            .receiver(receiver)
            .build();
    }

    public Friend accept() {
        return Friend.builder()
            .id(id)
            .sender(sender)
            .receiver(receiver)
            .isFriend(true)
            .build();
    }

}
