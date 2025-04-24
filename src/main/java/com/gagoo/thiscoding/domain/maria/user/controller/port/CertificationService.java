package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;

public interface CertificationService {
    AuthCodeRequest sendJoinCode(String email);
    AuthCodeRequest sendTemporaryPassword(String email);

    AuthCodeRequest checkAuthCode(AuthCodeRequest authCodeRequest);
}
