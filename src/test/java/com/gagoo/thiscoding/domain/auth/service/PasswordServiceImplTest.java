package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.maria.user.service.exception.PasswordNotEqualException;
import com.gagoo.thiscoding.domain.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceImplTest {

    private PasswordServiceImpl passwordService;

    @BeforeEach
    void init() {
        TestContainer testContainer = TestContainer.builder().build();

        passwordService = new PasswordServiceImpl(testContainer.passwordEncoderHolder);
    }

    @Test
    void matchPassword_비밀번호가_일치하면_예외가_발생하지_않는다() {
        // given
        String rawPassword = "1234";
        String encodedPassword = "encoded-1234";

        // when & then
        assertDoesNotThrow(() ->
                passwordService.matchPassword(rawPassword, encodedPassword)
        );
    }

    @Test
    void matchPassword_비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        String rawPassword = "12345";
        String encodedPassword = "encoded-1234";

        // when & then
        assertThrows(PasswordNotEqualException.class, () ->
                passwordService.matchPassword(rawPassword, encodedPassword)
        );
    }

    @Test
    void validatePasswordMatch_비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        String newPassword = "newpass123";
        String checkPassword = "wrongpass";

        // when & then
        assertThrows(PasswordNotEqualException.class, () ->
                passwordService.validatePasswordMatch(newPassword, checkPassword)
        );
    }

    @Test
    void validatePasswordMatch_비밀번호가_일치하면_예외가_발생하지_않는다() {
        // given
        String newPassword = "samepassword";
        String checkPassword = "samepassword";

        // when & then
        assertDoesNotThrow(() ->
                passwordService.validatePasswordMatch(newPassword, checkPassword)
        );
    }
}