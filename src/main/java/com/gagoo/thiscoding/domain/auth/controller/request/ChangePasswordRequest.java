package com.gagoo.thiscoding.domain.auth.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record ChangePasswordRequest(@NotBlank String currentPassword, @NotBlank String newPassword,
                                    @NotBlank String checkPassword) {
    @Builder
    public ChangePasswordRequest(@JsonProperty("currentPassword") String currentPassword, @JsonProperty("newPassword") String newPassword, @JsonProperty("checkPassword") String checkPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.checkPassword = checkPassword;
    }
}
