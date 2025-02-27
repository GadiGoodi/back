package com.gagoo.thiscoding.global.security.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeSecurityUtils implements SecurityUtils {

    private final String email;

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getUserEmail() {
        if (email == null) {
            throw new AuthorizationException(ErrorCode.USER_NOT_LOGIN);
        }

        return email;
    }

}