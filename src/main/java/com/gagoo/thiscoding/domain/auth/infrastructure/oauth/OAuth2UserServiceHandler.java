package com.gagoo.thiscoding.domain.auth.infrastructure.oauth;

import com.gagoo.thiscoding.domain.auth.domain.OAuth2UserInfo;
import com.gagoo.thiscoding.domain.auth.domain.OAuthUserInfoFactory;
import com.gagoo.thiscoding.domain.auth.exception.SocialAccountConflictException;
import com.gagoo.thiscoding.domain.auth.exception.SocialEmailNotProvidedException;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.infrastructure.ThisCodingAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceHandler extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    public OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new SocialEmailNotProvidedException(ErrorCode.SOCIAL_NOT_PROVIDED);
        }

        User user = getUserByEmail(oAuth2UserInfo);

        return new ThisCodingAuthentication(user);
    }


    /**
     * 소셜 로그인 정보를 우리 서비스에 맞는 형식으로 변환
     */
    private OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        return OAuthUserInfoFactory.getOAuthUserInfo(provider, attributes);
    }

    /**
     * 기존 사용자를 확인
     * - 없으면 새로 가입
     * - 있지만 다른 소셜 로그인 계정이면 에러 반환
     */
    private User getUserByEmail(OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);

        if (user == null) {
            user = registerUser(oAuth2UserInfo);
        } else if (user.getSocial().getValue() != oAuth2UserInfo.getProvider()) {
            throw new SocialAccountConflictException(ErrorCode.SOCIAL_ACCOUNT_CONFLICT);
        }

        return user;
    }

    /**
     * 소셜 로그인 회원가입
     */
    private User registerUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.oAuth2Register(
                oAuth2UserInfo.getEmail(),
                oAuth2UserInfo.getNickname(),
                oAuth2UserInfo.getProvider()
        );

        return userRepository.save(user);
    }

}
