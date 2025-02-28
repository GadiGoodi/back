package com.gagoo.thiscoding.domain.auth.controller.response;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final String role;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getImageUrl())
                .role(user.getRole().getValue())
                .build();
    }

}