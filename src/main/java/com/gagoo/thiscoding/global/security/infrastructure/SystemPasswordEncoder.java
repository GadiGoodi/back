package com.gagoo.thiscoding.global.security.infrastructure;

import com.gagoo.thiscoding.global.security.service.port.PasswordEncoderHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemPasswordEncoder implements PasswordEncoderHolder {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
