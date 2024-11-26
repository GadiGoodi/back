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
     */
    @Override
    public void sendJoinCode(String email) {
        Certification certification = mailSender.send(email);
        JoinCode joinCode = JoinCode.from(certification);

        joinCodeRepository.save(joinCode);
    }

}
