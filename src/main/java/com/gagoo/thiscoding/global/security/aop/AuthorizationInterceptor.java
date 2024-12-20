package com.gagoo.thiscoding.global.security.aop;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Collection;

public class AuthorizationInterceptor implements HandlerInterceptor {

    /**
     * 메서드가 실행되기 전에 권한이 있는지 확인하고
     * 권한이 있을 경우 메서드 싫랭
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuthorizationRequired annotation = getAnnotation(handler);

        if (annotation == null) {
            return true;
        }

        Collection<? extends GrantedAuthority> possibleAuthority = roleToAuthority(annotation.value());

        if (!hasAuthority(possibleAuthority)) {
            throw new AuthorizationException(ErrorCode.USER_NOT_LOGIN);
        }

        return true;
    }

    /**
     * 접근 가능한 권한이 하나라도 존재하는지 확인
     */
    private boolean hasAuthority(Collection<? extends GrantedAuthority> possibleAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getAuthorities()
                .stream().anyMatch(possibleAuthority::contains);
    }

    /**
     * 메서드에 AuthorizationRequired 어노테이션이 달려있는지 확인
     */
    private AuthorizationRequired getAnnotation(Object handler) {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethodAnnotation(AuthorizationRequired.class);
        }

        return null;
    }

    /**
     * 권한이 있는지 확인
     */
    private Collection<? extends GrantedAuthority> roleToAuthority(Role[] required) {
        return Arrays.stream(required)
                .map(Role::getValue)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}