package com.gagoo.thiscoding.domain.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(@NotBlank @Email String email, @NotBlank String newPassword, @NotBlank String checkPassword) {
}
