package com.gagoo.thiscoding.domain.mongo.code.infrastructure;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Document(collection = "code")
public class CodeDocument {

    @Id
    private String id;

    @Field(name = "room_id")
    private Long roomId;

    @Field(name = "writer_id")
    private Long writerId;

    @Field(name = "file_name")
    private String fileName;

    private String path;

    @Field(name = "save_date")
    private LocalDateTime saveDate;
}
