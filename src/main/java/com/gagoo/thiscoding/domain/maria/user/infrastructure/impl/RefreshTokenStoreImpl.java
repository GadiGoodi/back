package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.gagoo.thiscoding.global.security.JwtProperties.*;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RefreshTokenStoreImpl implements RefreshTokenStore {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 로그인할 때 리프레쉬 토큰 레디스에 저장
     */
    @Override
    public void storeToken(String username, String rtk) {
        redisTemplate.opsForValue().set(
                username,
                rtk,
                getRtkExpireTime(),
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * 레디스에 저장된 리프레쉬 토큰과 같은지 확인
     */
    @Transactional(readOnly = true)
    @Override
    public String getRtk(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 레디스에 저장된 토큰 삭제
     */
    @Override
    public void remove(String email) {
        redisTemplate.delete(email);
    }
}