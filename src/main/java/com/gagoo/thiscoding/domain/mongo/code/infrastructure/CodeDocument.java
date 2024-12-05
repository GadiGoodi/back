package com.gagoo.thiscoding.domain.mongo.code.infrastructure;

import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
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

    private String value;

    @Field(name = "save_date")
    private LocalDateTime saveDate;

    public Code toModel() {
        return Code.builder()
                .id(this.id)
                .roomId(this.roomId)
                .writerId(this.writerId)
                .fileName(this.fileName)
                .value(this.value)
                .saveDate(this.saveDate)
                .build();
    }

    public static CodeDocument from(Code code) {
        CodeDocument codeDocument = new CodeDocument();
        codeDocument.id = code.getId();
        codeDocument.roomId = code.getRoomId();
        codeDocument.writerId = code.getWriterId();
        codeDocument.fileName = code.getFileName();
        codeDocument.value = code.getValue();
        codeDocument.saveDate = code.getSaveDate();
        return codeDocument;
    }
}
