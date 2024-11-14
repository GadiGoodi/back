package com.gagoo.thiscoding.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final boolean isActive;
    private final boolean isBanned;
    private final Role role;
    private final Social social;

}
