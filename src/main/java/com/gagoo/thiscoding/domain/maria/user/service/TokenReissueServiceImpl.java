package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.TokenReissueService;
import com.gagoo.thiscoding.domain.maria.user.service.exception.TokenNotEquals;
import com.gagoo.thiscoding.domain.auth.service.port.TokenProvider;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;
import com.gagoo.thiscoding.global.security.config.JwtProperties;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.*;

@Service
@RequiredArgsConstructor
public class TokenReissueServiceImpl implements TokenReissueService {

    private final RefreshTokenStore refreshTokenStore;
    private final TokenProvider tokenProvider;
    private final HttpServletUtils httpServletUtils;
    private final JwtProperties jwtProperties;

    /**
     * 리프레쉬 토큰으로 엑세스 토큰 재발급
     */
    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
        String rtk = validateRtk(httpServletUtils.getCookie(request, AUTHORIZATION));

        String email = tokenProvider.getUsername(rtk);

        validateToken(rtk, email);

        String role = tokenProvider.getRole(rtk);
        String reissueAtk = tokenProvider.createAtk(email, role, jwtProperties.getAtkExpireTime());

        String reissueRtk = isReissueRtk(rtk) ?
                tokenProvider.createRtk(email, role, jwtProperties.getRtkExpireTime()) : rtk;

        reissueToken(response, reissueAtk, reissueRtk);
    }

    /**
     * 리프레쉬 토큰 갱신이 필요한지 확인
     */
    private boolean isReissueRtk(String rtk) {
        long expirationTime = tokenProvider.getExpirationTime(rtk);
        long currentTime = System.currentTimeMillis() / 1000;

        return (expirationTime - currentTime) < jwtProperties.getRtkExpireTime();
    }

    /**
     *  기존 리프레쉬 토큰 제거 및 엑세스토큰과 리프레쉬토큰 재발급
     */
    private void reissueToken(HttpServletResponse response, String reissueAtk, String reissueRtk) {
        httpServletUtils.setHeader(response, AUTHORIZATION, reissueAtk);
        httpServletUtils.addCookie(response, AUTHORIZATION, reissueRtk, jwtProperties.getRtkExpireTime());
    }

    /**
     * 리프레쉬 토큰 검증
     */
    private void validateToken(String rtk, String email) {
        validateTokenExpired(rtk);
        validateEqualsToken(rtk, email);
    }

    /**
     * 레디스에 저장된 토큰과 쿠키에서 추출한 토큰이 같은지 확인
     */
    private void validateEqualsToken(String rtk, String key) {
        if (!rtk.equals(refreshTokenStore.getRtk(key))) {
            new TokenNotEquals(ErrorCode.TOKEN_NOT_EQUALS);
        }
    }

    /**
     * 만료된 토큰인지 확인
     */
    private void validateTokenExpired(String rtk) {
        if (tokenProvider.isExpired(rtk)) {
            new GlobalException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    /**
     * 쿠키에 리프레쉬 토큰이 존재하면 리프레쉬 토큰 반환
     */
    private String validateRtk(Optional<Cookie> cookie) {
        return cookie
                .filter(c -> "rtk".equals(c.getName()))  // 쿠키 이름이 "rtk"인 경우만 필터링
                .map(c -> c.getValue().substring(TOKEN_PREFIX.length()))  // "Bearer "를 제거한 토큰 값 반환
                .orElseThrow(() -> new GlobalException(ErrorCode.TOKEN_NOT_FOUND));
    }

}
