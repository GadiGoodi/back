package com.gagoo.thiscoding.domain.auth.controller.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(@NotBlank String newPassword, @NotBlank String checkPassword) {
}
