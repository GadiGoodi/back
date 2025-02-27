package com.gagoo.thiscoding.domain.auth.controller.port;

import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;

public interface AuthService {
    User create(UserCreate userCreate);
    User getUserInfo();
    User changePassword(ChangePasswordRequest request);
    User resetPassword(ResetPasswordRequest request);
    LoginDto login(LoginRequest loginRequest);
    void removeToken();
}
