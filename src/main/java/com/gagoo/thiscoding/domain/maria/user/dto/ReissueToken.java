package com.gagoo.thiscoding.domain.maria.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueToken {
    private final String atk;

    @Builder
    public ReissueToken(String atk) {
        this.atk = atk;
    }

}