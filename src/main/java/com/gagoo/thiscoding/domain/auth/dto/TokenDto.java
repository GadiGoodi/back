package com.gagoo.thiscoding.domain.auth.dto;

import com.gagoo.thiscoding.domain.auth.domain.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    private final String atk;
    private final String rtk;
    private final Long rtkExpTime;

    public static TokenDto from(Token token) {
        return TokenDto.builder()
                .atk(token.getAtk())
                .rtk(token.getRtk())
                .rtkExpTime(token.getRtkExpTime())
                .build();
    }
}
