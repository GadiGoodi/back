package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.service.port.TokenFactory;
import com.gagoo.thiscoding.domain.auth.service.port.TokenProvider;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.security.config.JwtProperties;
import com.gagoo.thiscoding.domain.auth.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenFactoryImpl implements TokenFactory {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenStore refreshTokenStore;

    /**
     * 엑세스토큰과, 리프레쉬토큰 발급
     */
    @Override
    public Token createToken(User user) {
        String atk = tokenProvider.createAtk(user.getEmail(), user.getRole().getValue(), jwtProperties.getAtkExpireTime());
        String rtk = tokenProvider.createRtk(user.getEmail(), user.getRole().getValue(), jwtProperties.getRtkExpireTime());

        refreshTokenStore.storeToken(user.getEmail(), rtk);

        return Token.of(atk, rtk, jwtProperties.getRtkExpireTime());
    }

    /**
     * 로그아웃할 때 리프레시 토큰 삭제
     */
    @Override
    public void remove(String email) {
        refreshTokenStore.remove(email);
    }
}