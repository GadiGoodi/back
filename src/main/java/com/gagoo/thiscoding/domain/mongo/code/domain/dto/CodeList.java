package com.gagoo.thiscoding.domain.mongo.code.domain.dto;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodeList {
    private String codeId;
    private String fileName;
    private LocalDateTime saveDate;
    private String nickname;

    public static CodeList from(Code code) {
        return CodeList.builder()
                .codeId(code.getId())
                .fileName(code.getFileName())
                .saveDate(code.getSaveDate())
                .nickname(code.getNickname())
                .build();
    }
}
