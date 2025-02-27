package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.auth.service.port.PasswordEncoderHolder;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Social;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import lombok.Builder;
import lombok.Getter;

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

    /**
     * 회원가입
     */
    public static User create(UserCreate userCreate, PasswordEncoderHolder passwordEncoder) {
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

    /**
     * 소셜 로그인 회원가입
     */
    public static User oAuth2Register(String email, String nickname, String provider) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .isActivated(true)
                .isBanned(false)
                .role(Role.USER)
                .social(Social.valueOf(provider.toUpperCase()))
                .build();
    }

    /**
     * 닉네임 변경
     */
    public User updateNickname(String nickname) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(nickname)
                .imageUrl(this.imageUrl)
                .isActivated(this.isActivated)
                .isBanned(this.isBanned)
                .role(this.role)
                .social(this.social)
                .build();
    }

    /**
     * 프로필 이미지 변경
     */
    public User updateProfile(String imageUrl) {
        return User.builder()
                .id(this.id)
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

    /**
     * 비밀번호 변경
     */
    public User changePassword(User user, PasswordEncoderHolder passwordEncoder) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(passwordEncoder.encode(user.getPassword()))
                .nickname(this.nickname)
                .imageUrl(this.imageUrl)
                .isActivated(this.isActivated)
                .isBanned(this.isBanned)
                .role(this.role)
                .social(this.social)
                .build();
    }
}
