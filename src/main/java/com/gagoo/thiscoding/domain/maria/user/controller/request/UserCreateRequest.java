package com.gagoo.thiscoding.domain.maria.user.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    @Email @NotBlank
    private final String email;
    @NotBlank
    private final String nickname;
    @NotBlank
    private final String password;
    @NotBlank
    private final String checkPassword;

    @Builder
    public UserCreateRequest(String email, String nickname, String password, String checkPassword) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
