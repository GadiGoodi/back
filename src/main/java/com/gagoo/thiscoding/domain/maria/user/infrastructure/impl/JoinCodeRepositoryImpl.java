package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeRedis;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class JoinCodeRepositoryImpl implements JoinCodeRepository {
    private static final String CODE = ":code";
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 인증코드 레디스에 저장
     * @param joinCode 6자리 랜덤 숫자 인증코드
     */
    @Override
    public JoinCode save(JoinCode joinCode) {
        String key = joinCode.getEmail() + CODE;
        String value = joinCode.getCode();

        redisTemplate.opsForValue().set(key, value,  300, TimeUnit.SECONDS);

        return JoinCodeRedis.from(joinCode).toModel();
    }
}
