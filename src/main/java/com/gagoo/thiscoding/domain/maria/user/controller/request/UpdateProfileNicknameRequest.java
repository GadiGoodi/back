package com.gagoo.thiscoding.domain.maria.user.controller.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileNicknameRequest(@NotBlank String nickname) {
}
