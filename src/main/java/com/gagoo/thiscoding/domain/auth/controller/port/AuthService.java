package com.gagoo.thiscoding.domain.auth.controller.port;

import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UserCreateRequest;

public interface AuthService {
    User create(UserCreateRequest userCreateRequest);
    User changePassword(ChangePasswordRequest request);
    User resetPassword(ResetPasswordRequest request);
    User withdraw();
    LoginDto login(LoginRequest loginRequest);
    LoginDto getUserInfo();
    void removeToken();
}
