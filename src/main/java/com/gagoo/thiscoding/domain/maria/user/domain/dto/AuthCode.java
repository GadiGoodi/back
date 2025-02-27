package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record AuthCode(@NotBlank String email, @NotBlank String code) {

    @Builder
    public AuthCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public static AuthCode from(Certification certification) {
        return AuthCode.builder()
                .email(certification.getEmail())
                .code(certification.getCode())
                .build();
    }
}
