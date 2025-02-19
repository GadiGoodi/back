package com.gagoo.thiscoding.global.security.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
public class ThisCodingAuthentication extends UsernamePasswordAuthenticationToken implements OAuth2User {
    private final User user;

    public ThisCodingAuthentication(User user) {
        super(user, null, authorities(user.getRole()));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> authorities(Role role) {
        return Set.of(new SimpleGrantedAuthority(role.getValue()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
