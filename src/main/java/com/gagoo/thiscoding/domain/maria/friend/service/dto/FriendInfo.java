package com.gagoo.thiscoding.domain.maria.friend.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendInfo {
    private final Long id;
    private final String nickname;
    private final String imageUrl;

    @Builder
    public FriendInfo(Long id, String nickname, String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
