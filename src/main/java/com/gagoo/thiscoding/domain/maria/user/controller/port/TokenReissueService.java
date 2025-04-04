package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.auth.domain.Token;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenReissueService {
    Token create(HttpServletRequest request);
}
