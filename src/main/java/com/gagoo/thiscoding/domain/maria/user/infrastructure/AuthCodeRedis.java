package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;
import lombok.Getter;

@Getter
public class AuthCodeRedis {

    private String email;
    private String code;

    public static AuthCodeRedis from(AuthCodeRequest authCodeRequest) {
        AuthCodeRedis authCodeRedis = new AuthCodeRedis();
        authCodeRedis.email = authCodeRequest.email();
        authCodeRedis.code = authCodeRequest.code();

        return authCodeRedis;
    }

    public AuthCodeRequest toModel() {
        return AuthCodeRequest.of(email, code);
    }
}
