package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeRepository;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final MailSender mailSender;
    private final JoinCodeRepository joinCodeRepository;

    @Override
    public void sendJoinCode(String email) {
        String code = mailSender.send(email);
        joinCodeRepository.save(code);
    }
}
