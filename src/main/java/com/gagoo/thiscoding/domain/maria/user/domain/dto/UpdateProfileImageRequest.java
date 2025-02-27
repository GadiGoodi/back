package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record UpdateProfileImageRequest(@NotBlank String imageUrl) {
    @Builder
    public UpdateProfileImageRequest(@JsonProperty("imageUrl") String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

