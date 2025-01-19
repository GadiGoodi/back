package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CertificationServiceImplTest {

    public CertificationServiceImpl certificationService;

    @BeforeEach
    void init() {
        TestContainer testContainer = TestContainer.builder().build();

        this.certificationService = CertificationServiceImpl.builder()
                .mailSender(testContainer.mailSender)
                .joinCodeStore(testContainer.joinCodeStore)
                .build();
    }

    @Test
    void 이메일로_인증코드를_보낼_수_있다() {
        // given
        String email = "junsj1230@naver.com";

        // when
        JoinCode result = certificationService.sendJoinCode(email);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getCode()).isEqualTo("123456");
    }

    @Test
    void 인증코드가_일치하는지_확인할_수_있다() {
        // given
        String email = "junsj1230@naver.com";
        String code = "123456";
        JoinCode joinCode = JoinCode.builder()
                .email(email)
                .code(code)
                .build();

        // when
        JoinCode result = certificationService.checkJoinCode(joinCode);

        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getCode()).isEqualTo(code);
    }

}