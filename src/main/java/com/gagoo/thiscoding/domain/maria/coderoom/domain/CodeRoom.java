package com.gagoo.thiscoding.domain.maria.coderoom.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CodeRoom {

    private Long id;
    private UUID uuid;
    private String title;
    private String content;
    private String language;
    private int headCount;
}
