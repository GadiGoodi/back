package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final boolean isActivated;
    private final boolean isBanned;
    private final Role role;
    private final Social social;

}
