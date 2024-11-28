package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private String email;

    private String password;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Social social;

    public User toModel() {
        return User.builder()
            .id(this.id)
            .email(this.email)
            .password(this.password)
            .isActivated(this.isActivated)
            .isBanned(this.isBanned)
            .role(this.role)
            .social(this.social)
            .build();
    }
    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.password = user.getPassword();
        userEntity.isActivated = user.isActivated();
        userEntity.isBanned = user.isBanned();
        userEntity.role = user.getRole();
        userEntity.social = user.getSocial();
        return userEntity;

    }

}
