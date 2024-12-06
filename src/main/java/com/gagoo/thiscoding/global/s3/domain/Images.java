package com.gagoo.thiscoding.global.s3.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Images {

    private final String path;

    public static Images create(String hostUrl, String fileName) {
        return Images.builder()
                .path(hostUrl + fileName)
                .build();
    }
}
