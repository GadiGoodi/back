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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final SecurityService securityService;
    private final HttpServletUtils servletUtils;
    private final TokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            log.info("Token not found or invalid format");
            filterChain.doFilter(request, response);
            return;
        }

        String token = accessToken.split(" ")[1];

        // 토큰 유효성 검사
        tokenProvider.validateToken(token);

        String email = tokenProvider.getUsername(token);

        ThisCodingAuthentication authentication = (ThisCodingAuthentication) securityService.getAuthentication(email);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        return servletUtils.getHeader(request, "Authorization").orElse(null);
    }
}
