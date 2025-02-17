package com.gagoo.thiscoding.domain.auth.service.port;

public interface PasswordService {
    void matchPassword(String rawPassword, String encodedPassword);
}