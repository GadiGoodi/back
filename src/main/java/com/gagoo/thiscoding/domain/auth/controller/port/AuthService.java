package com.gagoo.thiscoding.domain.auth.controller.port;

import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;

public interface AuthService {
    LoginDto login(LoginRequest loginRequest);
}
