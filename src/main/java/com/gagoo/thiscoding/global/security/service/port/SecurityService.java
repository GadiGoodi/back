package com.gagoo.thiscoding.global.security.service.port;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    Authentication getAuthentication(String email);
}
