package com.gagoo.thiscoding.global.security.infrastructure.filter;

import com.gagoo.thiscoding.domain.auth.exception.ClaimNotFoundException;
import com.gagoo.thiscoding.domain.auth.exception.ExpiredJwtTokenException;
import com.gagoo.thiscoding.domain.auth.exception.MalformedFormJwtTokenException;
import com.gagoo.thiscoding.domain.auth.exception.UnsupportedJwtTokenException;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.gagoo.thiscoding.global.security.exception.ExceptionHandlingUtil.*;

@Component
public class AuthExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtTokenException |
                    MalformedFormJwtTokenException |
                    UnsupportedJwtTokenException |
                    ClaimNotFoundException |
                    UserNotFoundException |
                    AuthorizationException e) {
                setErrorResponse(response, e.getErrorCode());
        }
    }
}
