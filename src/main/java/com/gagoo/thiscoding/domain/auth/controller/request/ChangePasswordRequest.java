package com.gagoo.thiscoding.domain.auth.controller.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank String currentPassword,
                                    @NotBlank String newPassword,
                                    @NotBlank String checkPassword) {
}
