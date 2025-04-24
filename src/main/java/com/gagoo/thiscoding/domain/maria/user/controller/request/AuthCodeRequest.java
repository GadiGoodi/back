package com.gagoo.thiscoding.domain.maria.user.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AuthCodeRequest(@NotBlank String email, @NotBlank String code) {
    public static AuthCodeRequest of(String email, String code) {
        return new AuthCodeRequest(email, code);
    }
}
