package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLogin {

    @Email @NotBlank
    private final String email;
    @NotBlank
    private final String password;

    @Builder
    public UserLogin(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

}
