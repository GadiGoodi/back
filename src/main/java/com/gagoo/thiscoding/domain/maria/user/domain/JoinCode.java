package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinCode {

    @NotBlank
    private final String email;

    @NotBlank
    private final String code;

    @Builder
    public JoinCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public static JoinCode from(Certification certification) {
        return JoinCode.builder()
                .email(certification.getEmail())
                .code(certification.getCode())
                .build();
    }
}
