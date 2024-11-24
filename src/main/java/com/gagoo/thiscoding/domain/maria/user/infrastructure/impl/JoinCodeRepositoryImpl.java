package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.JoinCodeEntity;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa.JoinCodeJpaRepository;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JoinCodeRepositoryImpl implements JoinCodeRepository {

    private final JoinCodeJpaRepository joinCodeJpaRepository;

    /**
     * 인증코드 레디스에 저장
     * @param joinCode 6자리 랜덤 숫자 인증코드
     */
    @Override
    public void save(String joinCode) {
        JoinCodeEntity joinCodeEntity = new JoinCodeEntity(joinCode);
        joinCodeJpaRepository.save(joinCodeEntity);
    }


}
