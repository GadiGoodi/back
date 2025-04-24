package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;
import com.gagoo.thiscoding.domain.maria.user.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.AuthCodeStore;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final MailSender mailSender;
    private final UserRepository userRepository;
    private final AuthCodeStore authCodeStore;

    /**
     * 이메일에 인증 코드 발송
     * @param email 회원가입할 이메일
     */
    @Override
    public AuthCodeRequest sendJoinCode(String email) {
        Certification certification = mailSender.sendSignUpCode(email);
        AuthCodeRequest authCodeRequest = AuthCodeRequest.of(certification.getEmail(), certification.getCode());

        return authCodeStore.save(authCodeRequest);
    }

    /**
     * 이메일에 임시 비밀번호 발급
     * @param email 임시 비밀번호 발급받을 이메일
     */
    @Override
    public AuthCodeRequest sendTemporaryPassword(String email) {
        validateExistsUser(email);

        Certification certification = mailSender.sendResetPasswordCode(email);
        AuthCodeRequest authCodeRequest = AuthCodeRequest.of(certification.getEmail(), certification.getCode());

        return authCodeStore.save(authCodeRequest);
    }

    /**
     * 전송한 인증코드가 특정 이메일로 보낸 인증 코드인지 확인
     * @param authCodeRequest email, code 매핑 클래스
     */
    @Override
    public AuthCodeRequest checkAuthCode(AuthCodeRequest authCodeRequest) {
        return authCodeStore.
                checkAuthCode(authCodeRequest);
    }

    private void validateExistsUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
