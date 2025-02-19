package com.gagoo.thiscoding.domain.auth.domain;

import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OAuthUserInfoFactory {

    /**
     * 소셜 로그인 사용자 정보를 소셜타입에 맞게 변환
     */
    public static OAuth2UserInfo getOAuthUserInfo(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "naver" -> new NaverUserInfo(attributes);
            default -> throw new IllegalStateException("Unexpected value: " + provider);
        };
    }
}
