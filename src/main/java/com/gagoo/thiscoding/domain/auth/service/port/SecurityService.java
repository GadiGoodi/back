package com.gagoo.thiscoding.domain.auth.service.port;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    Authentication getAuthentication(String email);
}
