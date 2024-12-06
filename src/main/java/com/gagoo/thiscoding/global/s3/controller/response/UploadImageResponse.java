package com.gagoo.thiscoding.global.s3.controller.response;

import com.gagoo.thiscoding.global.s3.domain.Images;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadImageResponse {
    private final String path;

    public static UploadImageResponse from(Images images) {
        return UploadImageResponse.builder()
                .path(images.getPath())
                .build();
    }
}
