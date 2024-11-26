package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeRedis;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa.JoinCodeRedisRepository;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JoinCodeRepositoryImpl implements JoinCodeRepository {

    private final JoinCodeRedisRepository joinCodeRedisRepository;

    /**
     * 인증코드 레디스에 저장
     * @param joinCode 6자리 랜덤 숫자 인증코드
     */
    @Override
    public JoinCode save(JoinCode joinCode) {
        return joinCodeRedisRepository.save(JoinCodeRedis.from(joinCode)).toModel();
    }

}
