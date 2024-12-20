package com.gagoo.thiscoding.domain.maria.user.service.dto;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyInfo {

    private final String email;
    private final String nickname;
    private final Social social;
    private final boolean isTop10;

    public static MyInfo from(User user, boolean isTop10) {
        return MyInfo.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .social(user.getSocial())
                .isTop10(isTop10)
                .build();
    }
}
