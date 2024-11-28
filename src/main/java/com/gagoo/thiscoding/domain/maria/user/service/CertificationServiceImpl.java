package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeRepository;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final MailSender mailSender;
    private final JoinCodeRepository joinCodeRepository;

    /**
     * 이메일에 인증 코드 발송
     * @param email 회원가입할 이메일
     */
    @Override
    public void sendJoinCode(String email) {
        Certification certification = mailSender.send(email);
        JoinCode joinCode = JoinCode.from(certification);

        joinCodeRepository.save(joinCode);
    }

    /**
     * 전송한 인증코드 특정 이메일로 보낸 인증 코드인지 확인
     * @param joinCode email, code 매핑 클래스
     */
    @Override
    public void checkJoinCode(JoinCode joinCode) {
        joinCodeRepository.checkJoinCode(joinCode);
    }

}
