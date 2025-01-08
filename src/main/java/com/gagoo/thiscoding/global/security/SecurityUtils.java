package com.gagoo.thiscoding.global.security;

import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.CustomUserDetails;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    /**
     * SecurityContextHolder에 저장된 유저 정보 가져온 후 email 반환
     */
    public static String getUserEmail() {
        CustomUserDetails user = getUser();  // getUser()에서 예외 처리됨

        return user.getUsername();
    }

    /**
     * SecurityContextHolder에 저장된 유저 정보 반환
     */
    private static CustomUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthorizationException(ErrorCode.USER_NOT_LOGIN);
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }

}
