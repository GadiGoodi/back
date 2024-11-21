package com.gagoo.thiscoding.domain.mongo.chat.infrastructure;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Document(collection = "chat")
public class ChatDocument {

    @Id
    private String id;

    @Field(name = "room_id")
    private Long roomId;

    @Field(name = "user_id")
    private Long userId;

    private String content;

    @Field(name = "is_read")
    private boolean isRead;

    @Field(name = "send_date")
    private LocalDateTime sendDate;
}
