package com.gagoo.thiscoding.domain.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}