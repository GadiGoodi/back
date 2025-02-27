package com.gagoo.thiscoding.global.security.service;

import com.gagoo.thiscoding.global.security.service.port.SecurityService;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.security.infrastructure.ThisCodingAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    @Override
    public Authentication getAuthentication(String email) {
        return new ThisCodingAuthentication(userRepository.getByEmail(email));
    }
}
