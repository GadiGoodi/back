package com.gagoo.thiscoding.domain.file.controller.response;

import com.gagoo.thiscoding.domain.file.domain.Images;
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
