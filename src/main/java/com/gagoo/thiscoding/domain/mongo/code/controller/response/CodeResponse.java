package com.gagoo.thiscoding.domain.mongo.code.controller.response;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodeResponse {
    private Long writerId;
    private String fileName;
    private LocalDateTime saveDate;

    public static CodeResponse from(Code code) {
        return CodeResponse.builder()
                .writerId(code.getWriterId())
                .fileName(code.getFileName())
                .saveDate(code.getSaveDate())
                .build();
    }
}
