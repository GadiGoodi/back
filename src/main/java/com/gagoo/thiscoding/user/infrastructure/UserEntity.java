package com.gagoo.thiscoding.user.infrastructure;

import hexa.thiscoding.user.domain.Role;
import hexa.thiscoding.user.domain.Social;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private boolean isActive;

    private boolean isBanned;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Social social;

}
