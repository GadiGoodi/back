package com.gagoo.thiscoding.global.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtProperties {

    @Value("${spring.jwt.key")
    private static String key;
    @Value("${spring.jwt.atk}")
    private static Long accessTokenExpiration;
    @Value("${spring.jwt.rtk}")
    private static Long refreshTokenExpiration;
    public static final long REFRESH_TOKEN_REISSUE_TIME = 24 * 60 * 60;

    public static Long getAtkExpireTime() {
        return accessTokenExpiration;
    }

    public static Long getRtkExpireTime() {
        return refreshTokenExpiration;
    }

    public static String getSecretKey() {
        return key;
    }
}
