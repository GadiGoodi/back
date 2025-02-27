package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import lombok.Getter;

@Getter
public class AuthCodeRedis {

    private String email;
    private String code;

    public static AuthCodeRedis from(AuthCode authCode) {
        AuthCodeRedis authCodeRedis = new AuthCodeRedis();
        authCodeRedis.email = authCode.email();
        authCodeRedis.code = authCode.code();

        return authCodeRedis;
    }

    public AuthCode toModel() {
        return AuthCode.of(email, code);
    }
}
