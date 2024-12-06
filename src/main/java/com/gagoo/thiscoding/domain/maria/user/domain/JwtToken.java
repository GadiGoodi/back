package com.gagoo.thiscoding.domain.maria.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {

    private final String akt;
    private final String rtk;

    @Builder
    public JwtToken(String akt, String rtk) {
        this.akt = akt;
        this.rtk = rtk;
    }
}
