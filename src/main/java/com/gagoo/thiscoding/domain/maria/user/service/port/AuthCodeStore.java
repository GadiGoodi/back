package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;

public interface AuthCodeStore {
    AuthCodeRequest save(AuthCodeRequest authCodeRequest);
    AuthCodeRequest checkAuthCode(AuthCodeRequest authCodeRequest);
}
