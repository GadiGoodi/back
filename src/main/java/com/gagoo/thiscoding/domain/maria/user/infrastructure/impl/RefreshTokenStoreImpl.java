package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:config/application-jwt.properties")
public class RefreshTokenStoreImpl implements RefreshTokenStore {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.jwt.rtk}")
    private Long refreshTokenExpTime;

    /**
     * 로그인할 때 리프레쉬 토큰 레디스에 저장
     */
    public void storeToken(String username, String rtk) {
        redisTemplate.opsForValue().set(
                username,
                rtk,
                refreshTokenExpTime,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * 레디스에 저장된 리프레쉬 토큰과 같은지 확인
     */
    @Override
    public String getRtk(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}