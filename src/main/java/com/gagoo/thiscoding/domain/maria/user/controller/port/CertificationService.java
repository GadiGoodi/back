package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;

public interface CertificationService {
    AuthCode sendJoinCode(String email);
    AuthCode sendTemporaryPassword(String email);

    AuthCode checkAuthCode(AuthCode authCode);
}
