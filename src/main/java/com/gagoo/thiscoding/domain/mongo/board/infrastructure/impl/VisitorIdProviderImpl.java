package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.VisitorIdProvider;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VisitorIdProviderImpl implements VisitorIdProvider {

    private final SecurityUtils securityUtils;
    private final HttpServletUtils httpServletUtils;
    private final UuidHolder uuidHolder;

    /**
     * 방문자 ID 추출
     * - 로그인한 유저일 경우 유저의 이메일 반환
     * - 로그인하지 않은 유저의 경우 랜덤값 반환
     */
    @Override
    public String getVisitorId(HttpServletRequest request, HttpServletResponse response) {
        try {
            return securityUtils.getUserEmail();
        } catch (AuthorizationException e) {
            return getOrCreateVisitorId(request, response);
        }
    }

    /**
     * 로그인 하지 않은 방문자 등록을 위해 렌덤 visitorId값 쿠키에 저장 후 반환
     */
    private String getOrCreateVisitorId(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> visitorCookie = httpServletUtils.getCookie(request, "visitorId");

        if (visitorCookie.isPresent()) {
            return visitorCookie.get().getValue();
        }

        String visitorId = uuidHolder.random();
        httpServletUtils.addCookie(response, "visitorId", visitorId, 60L * 60 * 24 * 365);

        return visitorId;
    }
}
