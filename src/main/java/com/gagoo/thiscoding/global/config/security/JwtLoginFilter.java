package com.gagoo.thiscoding.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;

    public JwtLoginFilter(AuthenticationManager authManager) {
        this.authManager = authManager;

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

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                FilterChain chain,
                                                Authentication authResult)
            throws IOException, ServletException {
        System.out.println("로그인 성공했다");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed)
            throws IOException, ServletException {
        System.out.println("로그인 실패했다");
    }

}
