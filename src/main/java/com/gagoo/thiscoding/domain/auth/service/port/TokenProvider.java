package com.gagoo.thiscoding.domain.auth.service.port;

public interface TokenProvider {
    String createAtk(String email, String role, Long expTime);
    String createRtk(String email, String role, Long expTime);
    String getUsername(String token);
    String getRole(String token);
    Long getExpirationTime(String token);
    boolean isExpired(String token);
    void validateToken(String token);
}
