package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AuthCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AuthCodeNotMatchException;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.AuthCodeRedis;
import com.gagoo.thiscoding.domain.maria.user.service.port.AuthCodeStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;
import static com.gagoo.thiscoding.domain.maria.user.domain.contants.CodeKeyChain.*;
import static com.gagoo.thiscoding.global.exception.ErrorCode.*;

@Repository
@RequiredArgsConstructor
@Log4j2
public class AuthCodeStoreImpl implements AuthCodeStore {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 인증코드 레디스에 저장
     */
    @Override
    public AuthCode save(AuthCode authCode) {
        String key = authCode.email() + CODE;
        String value = authCode.code();
        int ttl = 300;

        redisTemplate.opsForValue().set(key, value,  ttl, TimeUnit.SECONDS);

        log.info("이메일 {}에 인증코드를 전송했습니다.", authCode.email());

        return AuthCodeRedis.from(authCode).toModel();
    }

    /**
     * 인증코드가 일치하는지와 null 체크
     * @param authCode email과 joinCode
     */
    @Override
    public AuthCode checkAuthCode(AuthCode authCode) {
        String key = authCode.email() + CODE;
        String value = authCode.code();

        matchAuthCode(key, value);

        return AuthCodeRedis.from(authCode).toModel();
    }

    /**
     * 요청받은 인증코드와 발급한 인증코드가 같은지 확인
     */
    private void matchAuthCode(String key, String value) {
        authCodeIsNull(key);

        if (!redisTemplate.opsForValue().get(key).equals(value))
            throw new AuthCodeNotMatchException(AUTH_CODE_NOT_MATCH);
    }

    /**
     * 인증코드 존재 여부 확인
     */
    private void authCodeIsNull(String key) {
        String value = redisTemplate.opsForValue().get(key);

        if (value == null || value.isBlank())
            throw new AuthCodeNotFoundException(AUTH_CODE_NOT_FOUND);
    }
}
