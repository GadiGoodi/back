package com.gagoo.thiscoding.domain.maria.user.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserLogin;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
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

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtUtilImpl jwtUtilImpl;
    private final RefreshTokenStore refreshTokenStore;

    public JwtLoginFilter(AuthenticationManager authManager, JwtUtilImpl jwtUtilImpl, RefreshTokenStore refreshTokenStore) {
        this.authManager = authManager;
        this.jwtUtilImpl = jwtUtilImpl;
        this.refreshTokenStore = refreshTokenStore;

        setFilterProcessesUrl("/api/auth/login");
    }

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
     * 엑세스 토큰 만료시 재발급을 위해 리프레쉬 토큰 redis에 저장
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

        String atk = jwtUtilImpl.createAtk(email, role);
        String rtk = jwtUtilImpl.createRtk(email, role);

        refreshTokenStore.storeToken(email, rtk);

        Cookie refreshTokenCookie = jwtUtilImpl.createRefreshTokenCookie(rtk);

        response.setHeader("Authorization", "Bearer" + atk);
        response.addCookie(refreshTokenCookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) {
        System.out.println("로그인 실패했다");
    }

}
