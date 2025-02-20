package com.gagoo.thiscoding.global.security.infrastructure.filter;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityService;
import com.gagoo.thiscoding.domain.auth.service.port.TokenProvider;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.security.infrastructure.ThisCodingAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
    public class JwtFilter extends OncePerRequestFilter {

        private final SecurityService securityService;
        private final HttpServletUtils servletUtils;
        private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);

        if (isValidTokenFormat(accessToken)) {
            log.info("Token not found or invalid format");
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(accessToken);
        processToken(token);
        filterChain.doFilter(request, response);
    }

    /**
     * 헤더에서 엑세스 토큰 추출
     */
    private String getAccessToken(HttpServletRequest request) {
        return servletUtils.getHeader(request, "Authorization").orElse(null);
    }

    /**
     * 토큰이 올바른 형식인지 확인
     */
    private boolean isValidTokenFormat(String token) {
        return token == null || !token.startsWith("Bearer ");
    }

    /**
     * 토큰 문자열에서 Bearer 접두어를 제거하고 실제 토큰 값만 추출
     */
    private String extractToken(String authHeader) {
        return authHeader.split(" ")[1];
    }

    /**
     * 토큰을 검증하고 사용자 인증 정보를 SecurityContext에 설정
     */
    private void processToken(String token) {
        tokenProvider.validateToken(token);
        String email = tokenProvider.getUsername(token);
        ThisCodingAuthentication authentication = (ThisCodingAuthentication) securityService.getAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
