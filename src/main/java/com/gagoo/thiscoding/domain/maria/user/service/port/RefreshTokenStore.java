package com.gagoo.thiscoding.domain.maria.user.service.port;

public interface RefreshTokenStore {
    void storeToken(String email, String rk);
    String getRtk(String key);
}
