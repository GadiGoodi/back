package com.gagoo.thiscoding.domain.maria.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Top10StatusResponse {

    private final boolean isTop10;

    public static Top10StatusResponse from(Boolean isTop10) {
        return Top10StatusResponse.builder()
                .isTop10(isTop10)
                .build();
    }

}
