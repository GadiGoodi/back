package com.gagoo.thiscoding.domain.maria.user.service.port;

public interface JwtUtil {
    String createAtk(String email, String role);
    String getUsername(String token);
    String getRole(String token);
    boolean isExpired(String token);
}
