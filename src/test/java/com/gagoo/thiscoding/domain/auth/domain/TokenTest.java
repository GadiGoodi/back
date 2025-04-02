package com.gagoo.thiscoding.domain.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.TOKEN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;


class TokenTest {

    @Test
    @DisplayName("of 메서드로 Token 객체 생성")
    void of_메서드로_토큰을_생성훌_수_있다() {
        // given
        String atk = "access-token";
        String rtk = "refresh-token";
        Long rtkExpTime = 3600L;

        // when
        Token token = Token.of(atk, rtk, rtkExpTime);

        // then
        assertThat(token.getAtk()).isEqualTo(atk);
        assertThat(token.getRtk()).isEqualTo(rtk);
        assertThat(token.getRtkExpTime()).isEqualTo(rtkExpTime);
    }

    @Test
    @DisplayName("getBearerAtk 메서드가 접두사를 포함")
    void getBearerAtk메서드는_접두사가_포함된_토큰을_반환한다() {
        // given
        String atk = "access-token";
        Token token = Token.of(atk, "refresh-token", 3600L);

        // when
        String bearerAtk = token.getBearerAtk();

        // then
        assertThat(bearerAtk).isEqualTo(TOKEN_PREFIX + atk);
        assertThat(bearerAtk).startsWith(TOKEN_PREFIX);
    }

}