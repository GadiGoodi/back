package com.gagoo.thiscoding.domain.auth.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record ResetPasswordRequest(@NotBlank String newPassword, @NotBlank String checkPassword) {
    @Builder
    public ResetPasswordRequest(@JsonProperty("newPassword") String newPassword, @JsonProperty("checkPassword") String checkPassword) {
        this.newPassword = newPassword;
        this.checkPassword = checkPassword;
    }
}
