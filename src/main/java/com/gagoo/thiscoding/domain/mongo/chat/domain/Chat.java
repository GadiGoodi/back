package com.gagoo.thiscoding.domain.mongo.chat.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Chat {
    private String id;
    private Long roomId;
    private Long userId;
    private String content;
    private boolean isRead;
    private LocalDateTime sendDate;
}
