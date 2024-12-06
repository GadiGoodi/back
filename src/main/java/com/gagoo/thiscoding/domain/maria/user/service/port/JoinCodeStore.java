package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;

public interface JoinCodeStore {
    JoinCode save(JoinCode joinCode);
    JoinCode checkJoinCode(JoinCode joinCode);
}
