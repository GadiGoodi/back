package com.gagoo.thiscoding.domain.maria.user.domain.contants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Social {
    THIS_CODING("thiscoding"), NAVER("naver");

    private final String value;
}
