package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.JoinCode;

public interface CertificationService {
    JoinCode sendJoinCode(String email);

    JoinCode checkJoinCode(JoinCode joinCode);
}
