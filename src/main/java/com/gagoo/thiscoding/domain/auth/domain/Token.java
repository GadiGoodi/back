package com.gagoo.thiscoding.domain.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.gagoo.thiscoding.global.security.common.AuthConstants.*;

@Builder
@Getter
@RequiredArgsConstructor
public class Token {
    private final String atk;
    private final String rtk;
    private final Long rtkExpTime;

    public static Token of(String atk, String rtk, Long rtkExpTime) {
        return new Token(atk, rtk, rtkExpTime);
    }

    public String getBearerAtk() {
        return TOKEN_PREFIX + atk;
    }
}
