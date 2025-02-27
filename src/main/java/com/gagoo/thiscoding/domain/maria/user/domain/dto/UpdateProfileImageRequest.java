package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileImageRequest(@NotBlank String imageUrl) {
}

