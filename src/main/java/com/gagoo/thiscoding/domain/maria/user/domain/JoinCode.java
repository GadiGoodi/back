package com.gagoo.thiscoding.domain.maria.user.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinCode {
    private String email;
    private String code;

    @Builder
    public JoinCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public static JoinCode from(Certification certification) {
        JoinCode joinCode = new JoinCode();
        joinCode.email = certification.getEmail();
        joinCode.code = certification.getCode();

        return joinCode;
    }
}
