package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;
import lombok.Getter;

@Getter
public class JoinCodeRedis {

    private String email;

    private String code;

    public static JoinCodeRedis from(JoinCode joinCode) {
        JoinCodeRedis joinCodeRedis = new JoinCodeRedis();
        joinCodeRedis.email = joinCode.getEmail();
        joinCodeRedis.code = joinCode.getCode();

        return joinCodeRedis;
    }

    public JoinCode toModel( ) {
        return JoinCode.builder()
                .email(email)
                .code(code)
                .build();
    }
}
