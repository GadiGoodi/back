package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.AuthCodeStore;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final MailSender mailSender;
    private final AuthCodeStore authCodeStore;

    /**
     * 이메일에 인증 코드 발송
     * @param email 회원가입할 이메일
     */
    @Override
    public AuthCode sendJoinCode(String email) {
        Certification certification = mailSender.sendSignUpCode(email);
        AuthCode authCode = AuthCode.of(certification.getEmail(), certification.getCode());

        return authCodeStore.save(authCode);
    }

    /**
     * 이메일에 임시 비밀번호 발급
     * @param email 임시 비밀번호 발급받을 이메일
     */
    @Override
    public AuthCode sendTemporaryPassword(String email) {
        Certification certification = mailSender.sendResetPasswordCode(email);
        AuthCode authCode = AuthCode.of(certification.getEmail(), certification.getCode());

        return authCodeStore.save(authCode);
    }

    /**
     * 전송한 인증코드가 특정 이메일로 보낸 인증 코드인지 확인
     * @param authCode email, code 매핑 클래스
     */
    @Override
    public AuthCode checkAuthCode(AuthCode authCode) {
        return authCodeStore.
                checkAuthCode(authCode);
    }

}
