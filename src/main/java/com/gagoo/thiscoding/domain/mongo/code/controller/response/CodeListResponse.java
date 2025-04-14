package com.gagoo.thiscoding.domain.mongo.code.controller.response;

import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeList;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodeListResponse {
    private String codeId;
    private String fileName;
    private LocalDateTime saveDate;
    private String nickname;

    public static CodeListResponse from (CodeList codeList) {
        return CodeListResponse.builder()
                .codeId(codeList.getCodeId())
                .fileName(codeList.getFileName())
                .saveDate(codeList.getSaveDate())
                .nickname(codeList.getNickname())
                .build();
    }
}
