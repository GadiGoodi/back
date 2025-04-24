package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.dto.Certification;

public interface MailSender {

    Certification sendSignUpCode(String email);
    Certification sendResetPasswordCode(String email);
}
