package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.JoinCodeNotMatchException;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeStore;

import java.util.HashMap;
import java.util.Map;

import static com.gagoo.thiscoding.global.exception.ErrorCode.JOIN_CODE_NOT_FOUND;
import static com.gagoo.thiscoding.global.exception.ErrorCode.JOIN_CODE_NOT_MATCH;

public class FakeJoinCodeStore implements JoinCodeStore {
    private final Map<String, String> store = new HashMap<>();

    @Override
    public JoinCode save(JoinCode joinCode) {
        store.put(joinCode.getEmail(), joinCode.getCode());
        return joinCode;
    }

    @Override
    public JoinCode checkJoinCode(JoinCode joinCode) {
        String savedCode = store.get(joinCode.getEmail());

        if (savedCode == null || savedCode.isBlank()) {
            throw new JoinCodeNotFoundException(JOIN_CODE_NOT_FOUND);
        }

        if (!savedCode.equals(joinCode.getCode())) {
            throw new JoinCodeNotMatchException(JOIN_CODE_NOT_MATCH);
        }

        return joinCode;
    }
}
