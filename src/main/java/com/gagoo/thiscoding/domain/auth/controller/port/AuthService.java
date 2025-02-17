package com.gagoo.thiscoding.global.security.controller.port;

import com.gagoo.thiscoding.global.security.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;

public interface AuthService {
    LoginDto login(LoginRequest loginRequest);
}
