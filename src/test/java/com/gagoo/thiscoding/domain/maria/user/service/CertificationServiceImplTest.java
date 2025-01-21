package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotMatchException;
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
        JoinCode result = certificationService.sendJoinCode(email);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getCode()).isNotNull();
        assertThat(result.getCode()).hasSize(6);
    }

    @Test
    void 인증코드가_일치하는지_확인할_수_있다() {
        // given
        String email = "junsj1230@naver.com";

        JoinCode sendJoinCode = certificationService.sendJoinCode(email);

        // when
        JoinCode joinCode = JoinCode.builder()
                .email(email)
                .code(sendJoinCode.getCode())
                .build();

        JoinCode result = certificationService.checkJoinCode(joinCode);

        // then
        assertThat(sendJoinCode.getEmail()).isEqualTo(result.getEmail());
        assertThat(sendJoinCode.getCode()).isEqualTo(result.getCode());
    }

    @Test
    void 잘못된_인증코드_입력시_예외가_발생한다() {
        // given
        String email = "junsj1230@naver.com";
        certificationService.sendJoinCode(email);

        // when
        JoinCode wrongJoinCode = JoinCode.builder()
                .email(email)
                .code("000000") // 잘못된 코드
                .build();

        // then
        assertThrows(JoinCodeNotMatchException.class,
                () -> certificationService.checkJoinCode(wrongJoinCode)
        );
    }

    @Test
    void 잘못된_이메일로_인증코드_검증을_요청시_예외가_발생한다() {
        // given
        String email = "junsj1230@naver.com";
        JoinCode sendJoinCode = certificationService.sendJoinCode(email);

        // when
        String wrongEmail = "worng@naver.com";

        JoinCode wrongJoinCode = JoinCode.builder()
                .email(wrongEmail)
                .code(sendJoinCode.getCode())
                .build();

        assertThrows(JoinCodeNotFoundException.class,
                () -> certificationService.checkJoinCode(wrongJoinCode));
    }

}