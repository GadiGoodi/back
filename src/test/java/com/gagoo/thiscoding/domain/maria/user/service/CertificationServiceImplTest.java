package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AuthCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AuthCodeNotMatchException;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CertificationServiceImplTest {

    public CertificationService certificationService;

    @BeforeEach
    void init() {
        TestContainer testContainer = TestContainer.builder().build();
        this.certificationService = testContainer.certificationService;
}

    @Test
    void 이메일로_인증코드를_보낼_수_있다() {
        // given
        String email = "junsj1230@naver.com";

        // when
        AuthCode result = certificationService.sendJoinCode(email);

        // then
        assertThat(result.email()).isEqualTo(email);
        assertThat(result.code()).isNotNull();
        assertThat(result.code()).hasSize(6);
    }

    @Test
    void 인증코드가_일치하는지_확인할_수_있다() {
        // given
        String email = "junsj1230@naver.com";

        AuthCode sendAuthCode = certificationService.sendJoinCode(email);

        // when
        AuthCode authCode = AuthCode.builder()
                .email(email)
                .code(sendAuthCode.code())
                .build();

        AuthCode result = certificationService.checkAuthCode(authCode);

        // then
        assertThat(sendAuthCode.email()).isEqualTo(result.email());
        assertThat(sendAuthCode.code()).isEqualTo(result.code());
    }

    @Test
    void 잘못된_인증코드_입력시_예외가_발생한다() {
        // given
        String email = "junsj1230@naver.com";
        certificationService.sendJoinCode(email);

        // when
        AuthCode wrongAuthCode = AuthCode.builder()
                .email(email)
                .code("000000") // 잘못된 코드
                .build();

        // then
        assertThrows(AuthCodeNotMatchException.class,
                () -> certificationService.checkAuthCode(wrongAuthCode)
        );
    }

    @Test
    void 잘못된_이메일로_인증코드_검증을_요청시_예외가_발생한다() {
        // given
        String email = "junsj1230@naver.com";
        AuthCode sendAuthCode = certificationService.sendJoinCode(email);

        // when
        String wrongEmail = "worng@naver.com";

        AuthCode wrongAuthCode = AuthCode.builder()
                .email(wrongEmail)
                .code(sendAuthCode.code())
                .build();

        assertThrows(AuthCodeNotFoundException.class,
                () -> certificationService.checkAuthCode(wrongAuthCode));
    }

}