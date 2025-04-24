package com.gagoo.thiscoding.domain.maria.user.controller.response;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private final String nickname;
    private final String imageUrl;

    @Builder
    public UserProfileResponse(String nickname, String imageUrl) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
            .nickname(user.getNickname())
            .imageUrl(user.getImageUrl())
            .build();
    }
}
