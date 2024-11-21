package com.gagoo.thiscoding.domain.mongo.code.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Code {
    private String id;
    private Long roomId;
    private Long writerId;
    private String fileName;
    private String path;
    private LocalDateTime saveDate;
}
