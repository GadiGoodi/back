package com.gagoo.thiscoding.domain.maria.user.controller.port;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenReissueService {
    String create(HttpServletRequest request);
}
