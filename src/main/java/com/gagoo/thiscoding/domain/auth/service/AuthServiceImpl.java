package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.security.controller.port.AuthService;
import com.gagoo.thiscoding.global.security.controller.request.LoginRequest;
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
    private final TokenFactory tokenFactory;

    @Override
    public LoginDto login(LoginRequest loginRequest) {
        User user = userRepository.getByEmail(loginRequest.getEmail());
        passwordService.matchPassword(loginRequest.getPassword(), user.getPassword());
        Token token = tokenFactory.createToken(user);

        return LoginDto.of(user, token);
    }

}
