package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final String imageUrl;
    private final boolean isActivated;
    private final boolean isBanned;
    private final Role role;
    private final Social social;

    @Builder
    public User(Long id, String email, String password, String nickname, String imageUrl, boolean isActivated, boolean isBanned, Role role, Social social) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.isActivated = isActivated;
        this.isBanned = isBanned;
        this.role = role;
        this.social = social;
    }

    public static User create(UserCreate userCreate, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(userCreate.getEmail())
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .nickname(userCreate.getNickname())
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.THIS_CODING)
                .build();
    }

    public User updateProfile(Long id, String imageUrl) {
        return User.builder()
                .id(id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .imageUrl(imageUrl)
                .isActivated(this.isActivated)
                .isBanned(this.isBanned)
                .role(this.role)
                .social(this.social)
                .build();
    }
}
