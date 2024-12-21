package com.gagoo.thiscoding.global.security;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.security.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    /**
     * SecurityContextHolder에 저장된 유저 정보 가져온 후 id 반환
     * @return
     */
    public static String getUserEmail() {
        CustomUserDetails user = getUser();
        return user == null ? null : user.getUsername();
    }

    /**
     * SecurityContextHolder에 저장된 유저 정보 반환
     */
    public static CustomUserDetails getUser() {
        return SecurityContextHolder.getContext().getAuthentication() != null ?
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()) :
                null;
    }
}
