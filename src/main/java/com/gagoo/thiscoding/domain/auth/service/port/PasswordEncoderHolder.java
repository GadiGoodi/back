package com.gagoo.thiscoding.domain.auth.service.port;

public interface PasswordEncoderHolder {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
