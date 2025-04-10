package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.auth.domain.Token;
import com.gagoo.thiscoding.domain.auth.service.port.TokenFactory;
import com.gagoo.thiscoding.domain.maria.user.domain.User;

import java.util.HashMap;
import java.util.Map;

public class FakeTokenFactory implements TokenFactory {

    private final FakeTokenProvider tokenProvider;
    private final Map<String, String> refreshTokens = new HashMap<>();

    public FakeTokenFactory(FakeTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Token createToken(User user) {
        String atk = tokenProvider.createAtk(user.getEmail(), user.getRole().getValue(), 1000 * 60 * 30L);
        String rtk = tokenProvider.createRtk(user.getEmail(), user.getRole().getValue(), 1000 * 60 * 60 * 24 * 7L);

        refreshTokens.put(user.getEmail(), rtk);

        return Token.of(atk, rtk, 1000 * 60 * 60 * 24 * 7L);
    }

    public String createTempAccessToken(User user) {
        long tempExpireTime = 60 * 1000L;

        return tokenProvider.createAtk(
                user.getEmail(),
                user.getRole().getValue(),
                tempExpireTime
        );
    }

    @Override
    public void remove(String email) {
        refreshTokens.remove(email);
    }

    // 테스트용 헬퍼 메소드
    public String getRefreshToken(String email) {
        return refreshTokens.get(email);
    }

    public boolean hasRefreshToken(String email) {
        return refreshTokens.containsKey(email);
    }
}