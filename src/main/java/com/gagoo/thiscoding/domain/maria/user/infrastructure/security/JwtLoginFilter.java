package com.gagoo.thiscoding.domain.maria.user.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserLogin;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.global.utils.HttpServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static com.gagoo.thiscoding.global.security.JwtProperties.*;
import static com.gagoo.thiscoding.global.security.constants.SecurityConstants.*;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtUtilImpl jwtUtilImpl;
    private final RefreshTokenStore refreshTokenStore;
    private final HttpServletUtils httpServletUtils;

    public JwtLoginFilter(AuthenticationManager authManager, JwtUtilImpl jwtUtilImpl, RefreshTokenStore refreshTokenStore, HttpServletUtils httpServletUtils) {
        this.authManager = authManager;
        this.jwtUtilImpl = jwtUtilImpl;
        this.refreshTokenStore = refreshTokenStore;
        this.httpServletUtils = httpServletUtils;

        setFilterProcessesUrl("/api/auth/login");
    }

    /**
     * json 객체로 넘어온 데이터를 역직렬화하여 로그인 검증
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLogin loginData = new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginData.getEmail(), loginData.getPassword());

            return authManager.authenticate(authToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 로그인 성공시 엑세스 토큰과 리프레쉬 토큰 발급
     * 엑세스 토큰 만료시 재발급을 위해 리프레쉬 토큰 레디스에 저장
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String atk = jwtUtilImpl.createAtk(email, role, getAtkExpireTime());
        String rtk = jwtUtilImpl.createRtk(email, role, getRtkExpireTime());

        refreshTokenStore.storeToken(email, rtk);

        httpServletUtils.setHeader(response, AUTHORIZATION, BEARER_PREFIX + atk);
        httpServletUtils.addCookie(response, AUTHORIZATION, BEARER_PREFIX + atk, getRtkExpireTime().intValue());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) {
        System.out.println("로그인 실패했다");
    }

}
