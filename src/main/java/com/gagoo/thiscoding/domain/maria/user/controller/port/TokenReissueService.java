package com.gagoo.thiscoding.domain.maria.user.controller.port;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenReissueService {
    void create(HttpServletRequest request, HttpServletResponse response);
}
