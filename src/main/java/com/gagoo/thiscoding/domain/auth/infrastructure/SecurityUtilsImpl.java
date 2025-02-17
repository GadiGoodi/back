package com.gagoo.thiscoding.domain.auth.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtilsImpl implements SecurityUtils {

    /**
     * SecurityContextHolder에 저장된 유저 정보 가져온 후 email 반환
     */
    @Override
    public String getUserEmail() {
        User user = getUser();  // getUser()에서 예외 처리됨

        return user.getEmail();
    }

    /**
     * SecurityContextHolder에 저장된 유저 정보 반환
     */
    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthorizationException(ErrorCode.USER_NOT_LOGIN);
        }

        return (User) authentication.getPrincipal();
    }

}
