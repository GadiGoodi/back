package com.gagoo.thiscoding.domain.maria.user.controller.response;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.user.service.dto.MyInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyInfoResponse {

    private final String email;
    private final String nickname;
    private final Social social;
    private final boolean isTop10;

    public static MyInfoResponse from(MyInfo myInfo) {
        return MyInfoResponse.builder()
                .email(myInfo.getEmail())
                .nickname(myInfo.getNickname())
                .social(myInfo.getSocial())
                .isTop10(myInfo.isTop10())
                .build();
    }

}
