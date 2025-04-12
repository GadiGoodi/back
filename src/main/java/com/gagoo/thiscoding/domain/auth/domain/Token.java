package com.gagoo.thiscoding.domain.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
