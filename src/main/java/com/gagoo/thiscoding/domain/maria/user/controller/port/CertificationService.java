package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;

public interface CertificationService {
    void sendJoinCode(String email);

    void checkJoinCode(JoinCode joinCode);
}
