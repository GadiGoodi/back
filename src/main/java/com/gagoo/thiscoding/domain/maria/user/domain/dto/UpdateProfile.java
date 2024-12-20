package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateProfile {

    private final String imageUrl;

    @Builder
    public UpdateProfile(@JsonProperty("imageUrl") String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

