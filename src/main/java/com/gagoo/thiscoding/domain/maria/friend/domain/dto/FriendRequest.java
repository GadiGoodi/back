package com.gagoo.thiscoding.domain.maria.friend.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendRequest {
    private final Long senderId;
    private final Long receiverId;

    @Builder
    public FriendRequest(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
