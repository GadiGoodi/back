package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.auth.service.port.PasswordEncoderHolder;

public class FakePasswordEncoder implements PasswordEncoderHolder {
    @Override
    public String encode(String password) {
        return "encoded-" + password;
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return false;
    }
}
