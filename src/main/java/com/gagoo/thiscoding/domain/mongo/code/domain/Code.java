package com.gagoo.thiscoding.domain.mongo.code.domain;

import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Code {
    private String id;
    private Long roomId;
    private Long writerId;
    private String fileName;
    private String value;
    private LocalDateTime saveDate;

    @Builder
    public Code(String id, Long roomId, Long writerId, String fileName, String value, LocalDateTime saveDate) {
        this.id = id;
        this.roomId = roomId;
        this.writerId = writerId;
        this.fileName = fileName;
        this.value = value;
        this.saveDate = saveDate;
    }

    public static Code create(CodeCreate codeCreate) {
        return Code.builder()
                .roomId(codeCreate.getRoomId())
                .writerId(codeCreate.getWriterId())
                .fileName(codeCreate.getFileName())
                .value(codeCreate.getValue())
                .saveDate(LocalDateTime.now())
                .build();
    }
}
