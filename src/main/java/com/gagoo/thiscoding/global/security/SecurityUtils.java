package com.gagoo.thiscoding.global.security;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    /**
     * SecurityContextHolder에 저장된 유저 정보 가져온 후 id 반환
     * @return
     */
    public static Long getUserId() {
        User user = getUser();
        return user == null ? null : user.getId();
    }

    /**
     * SecurityContextHolder에 저장된 유저 정보 반환
     */
    public static User getUser() {
        return SecurityContextHolder.getContext().getAuthentication() != null ?
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()) :
                null;
    }
}
