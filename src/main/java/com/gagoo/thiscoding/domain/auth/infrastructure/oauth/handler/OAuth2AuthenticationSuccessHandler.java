package com.gagoo.thiscoding.domain.auth.infrastructure.oauth.handler;

import com.gagoo.thiscoding.domain.auth.exception.InvalidAuthenticationException;
import com.gagoo.thiscoding.domain.auth.service.port.TokenFactory;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.infrastructure.ThisCodingAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String REDIRECT_URI = "http://localhost:3000";
    private final HttpServletUtils servletUtils;
    private final TokenFactory tokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2AuthenticationSuccessHandler::onAuthenticationSuccess");

        if (isResponseCommitted(response)) {
            return;
        }

        ThisCodingAuthentication thisCodingAuthentication = parseToThisCodingAuthentication(authentication);
        String tempAccessToken = generateTempAccessToken(thisCodingAuthentication);
        String redirectUrl = getRedirectUrl(tempAccessToken);

        performRedirect(request, response, redirectUrl);
    }

    /**
     * 응답이 이미 커밋되었는지 확인
     */
    private boolean isResponseCommitted(HttpServletResponse response) {
        if (response.isCommitted()) {
            log.info("응답이 이미 커밋되었습니다.");
            return true;
        }
        return false;
    }

    /**
     * 리다이렉트 url 생성
     */
    private static String getRedirectUrl(String tempAccessToken) {
        return UriComponentsBuilder
                .fromUriString(REDIRECT_URI)
                .queryParam("tempToken", tempAccessToken)
                .build()
                .toUriString();
    }

    /**
     * 임시 액세스 토큰 생성
     */
    private String generateTempAccessToken(ThisCodingAuthentication authentication) {
        return tokenFactory.createTempAccessToken(authentication.getUser());
    }

    /**
     * 리다이렉트 수행
     */
    private void performRedirect(HttpServletRequest request, HttpServletResponse response, String redirectUrl) throws IOException {
        this.clearAuthenticationAttributes(request, response);
        this.getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    /**
     * 인증 성공 후 불필요한 인증 관련 데이터를 정리.
     */
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        servletUtils.removeCookie(request, response, "oauth2_auth_request");
    }

    /**
     * Authentication 객체를 ThisCodingAuthentication으로 변환.
     * - OAuth2 인증 객체인 경우 ThisCodingAuthentication으로 반환.
     * - ThisCodingAuthentication 타입이 아닐 경우 예외 발생
     */
    private ThisCodingAuthentication parseToThisCodingAuthentication(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oAuthToken) {
            if (oAuthToken.getPrincipal() instanceof ThisCodingAuthentication thisCodingAuth) {
                return thisCodingAuth;
            }
        }
        throw new InvalidAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
    }
}