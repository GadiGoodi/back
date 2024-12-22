package com.gagoo.thiscoding.domain.maria.user.service.port;

public interface JwtUtil {
    String createAtk(String email, String role, Long expTime);
    String createRtk(String email, String role, Long expTime);
    String getUsername(String token);
    String getRole(String token);
    Long getExpirationTime(String token);
    boolean isExpired(String token);
}
