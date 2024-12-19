package com.gagoo.thiscoding.domain.maria.user.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateProfile {

    private final String imageUrl;

    @Builder
    public UpdateProfile(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
