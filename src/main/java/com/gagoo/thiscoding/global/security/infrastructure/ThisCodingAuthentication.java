package com.gagoo.thiscoding.global.security.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
public class ThisCodingAuthentication extends UsernamePasswordAuthenticationToken {
    private final User user;

    public ThisCodingAuthentication(User user) {
        super(user, null, authorities(user.getRole()));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> authorities(Role role) {
        return Set.of(new SimpleGrantedAuthority(role.getValue()));
    }
}
