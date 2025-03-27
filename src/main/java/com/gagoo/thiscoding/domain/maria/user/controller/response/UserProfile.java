package com.gagoo.thiscoding.domain.maria.user.controller.response;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private final String nickname;
    private final String imageUrl;

    @Builder
    public UserProfile(String nickname, String imageUrl) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static UserProfile from(User user) {
        return UserProfile.builder()
            .nickname(user.getNickname())
            .imageUrl(user.getImageUrl())
            .build();
    }
}
