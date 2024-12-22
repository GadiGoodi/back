package com.gagoo.thiscoding.global.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    @Value("${spring.jwt.key")
    private String secretKey;
    @Value("${spring.jwt.atk}")
    private Long atkExpireTime;
    @Value("${spring.jwt.rtk}")
    private Long rtkExpireTime;
    public static final long REFRESH_TOKEN_REISSUE_TIME = 24 * 60 * 60;

}

