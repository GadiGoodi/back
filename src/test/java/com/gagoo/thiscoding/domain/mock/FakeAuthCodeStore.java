package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import com.gagoo.thiscoding.domain.maria.user.exception.AuthCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.exception.AuthCodeNotMatchException;
import com.gagoo.thiscoding.domain.maria.user.service.port.AuthCodeStore;

import java.util.HashMap;
import java.util.Map;

import static com.gagoo.thiscoding.global.exception.ErrorCode.AUTH_CODE_NOT_FOUND;
import static com.gagoo.thiscoding.global.exception.ErrorCode.AUTH_CODE_NOT_MATCH;

public class FakeAuthCodeStore implements AuthCodeStore {
    private final Map<String, String> store = new HashMap<>();

    @Override
    public AuthCode save(AuthCode authCode) {
        store.put(authCode.email(), authCode.code());
        return authCode;
    }

    @Override
    public AuthCode checkAuthCode(AuthCode authCode) {
        String savedCode = store.get(authCode.email());

        if (savedCode == null || savedCode.isBlank()) {
            throw new AuthCodeNotFoundException(AUTH_CODE_NOT_FOUND);
        }

        if (!savedCode.equals(authCode.code())) {
            throw new AuthCodeNotMatchException(AUTH_CODE_NOT_MATCH);
        }

        return authCode;
    }
}
