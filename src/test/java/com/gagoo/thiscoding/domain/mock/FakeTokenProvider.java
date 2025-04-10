package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.auth.service.port.TokenProvider;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FakeTokenProvider implements TokenProvider {

    private final Map<String, String> emailMap = new HashMap<>();
    private final Map<String, String> roleMap = new HashMap<>();
    private final Map<String, Long> expirationMap = new HashMap<>();
    private final Set<String> expiredTokens = new HashSet<>();
    private final UuidHolder uuidHolder;

    public FakeTokenProvider(UuidHolder uuidHolder) {
        this.uuidHolder = uuidHolder;
    }

    @Override
    public String getUsername(String token) {
        return emailMap.get(token);
    }

    @Override
    public String getRole(String token) {
        return roleMap.get(token);
    }

    @Override
    public Long getExpirationTime(String token) {
        return expirationMap.get(token);
    }

    @Override
    public boolean isExpired(String token) {
        return expiredTokens.contains(token);
    }

    @Override
    public String createAtk(String email, String role, Long expTime) {
        return createFakeToken(email, role, expTime);
    }

    @Override
    public String createRtk(String email, String role, Long expTime) {
        return createFakeToken(email, role, expTime);
    }

    @Override
    public void validateToken(String token) {
        if (!emailMap.containsKey(token)) {
            throw new RuntimeException("Invalid token");
        }
        if (expiredTokens.contains(token)) {
            throw new RuntimeException("Token expired");
        }
    }

    private String createFakeToken(String email, String role, Long expTime) {
        String token = uuidHolder.random();
        emailMap.put(token, email);
        roleMap.put(token, role);
        expirationMap.put(token, System.currentTimeMillis() / 1000 + expTime);
        return token;
    }

    // 테스트 코드에서 명시적으로 토큰을 만료
    public void expireToken(String token) {
        expiredTokens.add(token);
    }
}