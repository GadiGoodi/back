package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;

public interface JoinCodeRepository {
    JoinCode save(JoinCode joinCode);

    JoinCode checkJoinCode(JoinCode joinCode);
}
