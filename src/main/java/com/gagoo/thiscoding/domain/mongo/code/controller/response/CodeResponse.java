package com.gagoo.thiscoding.domain.mongo.code.controller.response;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodeResponse {
    private String id;
    private String fileName;
    private String value;

    public static CodeResponse from(Code code) {
        return CodeResponse.builder()
                .id(code.getId())
                .fileName(code.getFileName())
                .value(code.getValue())
                .build();
    }
}
