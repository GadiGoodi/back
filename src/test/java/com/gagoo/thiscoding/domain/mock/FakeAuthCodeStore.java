package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;
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
    public AuthCodeRequest save(AuthCodeRequest authCodeRequest) {
        store.put(authCodeRequest.email(), authCodeRequest.code());
        return authCodeRequest;
    }

    @Override
    public AuthCodeRequest checkAuthCode(AuthCodeRequest authCodeRequest) {
        String savedCode = store.get(authCodeRequest.email());

        if (savedCode == null || savedCode.isBlank()) {
            throw new AuthCodeNotFoundException(AUTH_CODE_NOT_FOUND);
        }

        if (!savedCode.equals(authCodeRequest.code())) {
            throw new AuthCodeNotMatchException(AUTH_CODE_NOT_MATCH);
        }

        return authCodeRequest;
    }
}
