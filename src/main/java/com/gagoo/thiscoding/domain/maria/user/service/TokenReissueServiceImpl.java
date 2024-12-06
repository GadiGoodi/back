package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.TokenReissueService;
import com.gagoo.thiscoding.domain.maria.user.service.exception.TokenNotEquals;
import com.gagoo.thiscoding.domain.maria.user.service.port.JwtUtil;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenReissueServiceImpl implements TokenReissueService {

    private final RefreshTokenStore refreshTokenStore;
    private final JwtUtil jwtUtil;

    /**
     * 리프레쉬 토큰으로 엑세스 토큰 재발급
     */
    @Override
    public String create(HttpServletRequest request) {
        String rtk = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            rtk = validateRtk(cookies);
        }
        String email = jwtUtil.getUsername(rtk);
        validateTokenExpired(rtk);

        if (rtk != null) {
            validateEqualsToken(rtk, email);
        }

        String role = jwtUtil.getRole(rtk);

        return jwtUtil.createAtk(email, role);
    }

    /**
     * 레디스에 저장된 토큰과 쿠키에서 추출한 토큰이 같은지 확인
     */
    private void validateEqualsToken(String rtk, String key) {
        if (!rtk.equals(refreshTokenStore.getRtk(key))) {
            throw new TokenNotEquals(ErrorCode.TOKEN_NOT_EQUALS);
        }
    }

    /**
     * 만료된 토큰인지 확인
     */
    private void validateTokenExpired(String rtk) {
        if (jwtUtil.isExpired(rtk)) {
            throw new GlobalException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    /**
     * 쿠키에 리프레쉬 토큰이 존재하면 리프레쉬 토큰 반환
     */
    private static String validateRtk(Cookie[] cookies) {
        String rtk = null;

        for (Cookie cookie : cookies) {
            if ("rtk".equals(cookie.getName())) {
                rtk = cookie.getValue().substring(6);
                break;
            }
        }
        return rtk;
    }
}
