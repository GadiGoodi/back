package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final boolean isActivated;
    private final boolean isBanned;
    private final Role role;
    private final Social social;

    @Builder
    public User (Long id, String email, String password, boolean isActivated, boolean isBanned, Role role, Social social) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActivated = isActivated;
        this.isBanned = isBanned;
        this.role = role;
        this.social = social;
    }
}
