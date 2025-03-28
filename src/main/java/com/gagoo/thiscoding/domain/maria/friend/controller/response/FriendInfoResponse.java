package com.gagoo.thiscoding.domain.maria.friend.controller.response;

import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import jakarta.persistence.Id;
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
    
    public static FriendInfoResponse from(FriendInfo friendInfo) {
        return FriendInfoResponse.builder()
            .id(friendInfo.getId())
            .nickname(friendInfo.getNickname())
            .imageUrl(friendInfo.getImageUrl())
            .build();
    }
}
