package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotMatchException;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeRedis;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeStore;
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
public class JoinCodeStoreImpl implements JoinCodeStore {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 인증코드 레디스에 저장
     * @param joinCode 6자리 랜덤 숫자 인증코드
     */
    @Override
    public JoinCode save(JoinCode joinCode) {
        String key = joinCode.getEmail() + CODE;
        String value = joinCode.getCode();
        int ttl = 300;

        redisTemplate.opsForValue().set(key, value,  ttl, TimeUnit.SECONDS);

        log.info("이메일 {}에 인증코드를 전송했습니다.", joinCode.getEmail());

        return JoinCodeRedis.from(joinCode).toModel();
    }

    /**
     * 인증코드가 일치하는지와 null 체크
     * @param joinCode email과 joinCode
     */
    @Override
    public JoinCode checkJoinCode(JoinCode joinCode) {
        String key = joinCode.getEmail() + CODE;
        String value = joinCode.getCode();

        matchJoinCode(key, value);

        return JoinCodeRedis.from(joinCode).toModel();
    }

    /**
     * 요청받은 인증코드와 발급한 인증코드가 같은지 확인
     */
    private void matchJoinCode(String key, String value) {

        joinCodeIsNull(key);

        if (!redisTemplate.opsForValue().get(key).equals(value))
            throw new JoinCodeNotMatchException(JOIN_CODE_NOT_MATCH);
    }

    /**
     * 인증코드 존재 여부 확인
     */
    private void joinCodeIsNull(String key) {
        String value = redisTemplate.opsForValue().get(key);

        if (value == null || value.isBlank())
            throw new JoinCodeNotFoundException(JOIN_CODE_NOT_FOUND);
    }
}
