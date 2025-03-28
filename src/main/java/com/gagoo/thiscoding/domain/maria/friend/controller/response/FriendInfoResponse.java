package com.gagoo.thiscoding.domain.maria.friend.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendInfoResponse {
    private Long id;
    private String nickname;
    private String imageUrl;

    @Builder
    public FriendInfoResponse(Long id, String nickname, String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
