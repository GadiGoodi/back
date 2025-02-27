package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthCode(@NotBlank String email, @NotBlank String code) {
    public static AuthCode of(String email, String code) {
        return new AuthCode(email, code);
    }
}
