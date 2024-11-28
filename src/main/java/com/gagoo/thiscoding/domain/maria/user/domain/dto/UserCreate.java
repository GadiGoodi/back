package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    @Email @NotBlank
    private final String email;
    @NotBlank
    private final String nickname;
    @NotBlank
    private final String password;
    @NotBlank
    private final String checkPassword;

    @Builder
    public UserCreate(String email, String nickname, String password, String checkPassword) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
