package com.gagoo.thiscoding.global.security.infrastructure.filter;

import com.gagoo.thiscoding.domain.auth.exception.ClaimNotFoundException;
import com.gagoo.thiscoding.domain.auth.exception.ExpiredJwtTokenException;
import com.gagoo.thiscoding.domain.auth.exception.MalformedFormJwtTokenException;
import com.gagoo.thiscoding.domain.auth.exception.UnsupportedJwtTokenException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.ExceptionHandlingUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.gagoo.thiscoding.global.security.exception.ExceptionHandlingUtil.*;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtTokenException e) {
            setErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
        } catch (MalformedFormJwtTokenException e) {
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        } catch (UnsupportedJwtTokenException e) {
            setErrorResponse(response, ErrorCode.INVALID_SIGNATURE);
        } catch (ClaimNotFoundException e) {
            setErrorResponse(response, ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
