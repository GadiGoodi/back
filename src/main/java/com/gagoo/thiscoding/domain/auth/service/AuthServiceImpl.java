package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.controller.port.AuthService;
import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.auth.domain.Token;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.auth.service.port.TokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final SecurityUtils securityUtils;
    private final TokenFactory tokenFactory;

    /**
     * 로그인
     */
    @Override
    public LoginDto login(LoginRequest loginRequest) {
        User user = userRepository.getByEmail(loginRequest.getEmail());
        passwordService.matchPassword(loginRequest.getPassword(), user.getPassword());
        Token token = tokenFactory.createToken(user);

        return LoginDto.of(user, token);
    }

    @Override
    public User changePassword(ChangePasswordRequest request) {
        User user = userRepository.getByEmail(securityUtils.getUserEmail());
        passwordService.matchPassword(request.currentPassword(), user.getPassword());

        return updatePassword(user, request.newPassword(), request.checkPassword());
    }

    @Override
    public User resetPassword(ResetPasswordRequest request) {
        User user = userRepository.getByEmail(securityUtils.getUserEmail());

        return updatePassword(user, request.newPassword(), request.checkPassword());
    }

    @Override
    public User getUserInfo() {
        return securityUtils.getUser();
    }

    private User updatePassword(User user, String newPassword, String checkPassword) {
        passwordService.validatePasswordMatch(newPassword, checkPassword);
        User user = user.changePassword(passwordService.encode(newPassword));
        
        return userRepository.save(user);
    }

}
