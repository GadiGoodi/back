package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;

public interface AuthCodeStore {
    AuthCode save(AuthCode authCode);
    AuthCode checkAuthCode(AuthCode authCode);
}
