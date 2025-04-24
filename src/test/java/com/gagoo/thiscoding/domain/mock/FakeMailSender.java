package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;

import java.security.SecureRandom;

public class FakeMailSender implements MailSender {

    @Override
    public Certification sendSignUpCode(String email) {
        return new Certification(email, generateSecureCode());
    }

    @Override
    public Certification sendResetPasswordCode(String email) {
        return null;
    }

    private String generateSecureCode() {
        SecureRandom secureRandom = new SecureRandom();

        return String.valueOf(
                secureRandom.nextInt(900000) + 100000
        );
    }
}
