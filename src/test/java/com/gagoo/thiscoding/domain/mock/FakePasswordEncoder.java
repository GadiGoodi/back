package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.global.security.service.port.PasswordEncoderHolder;

public class FakePasswordEncoder implements PasswordEncoderHolder {
    @Override
    public String encode(String password) {
        return "encoded-" + password;
    }
}
