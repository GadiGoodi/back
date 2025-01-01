package com.gagoo.thiscoding.domain.mongo.answer.infrastructure;

import com.gagoo.thiscoding.domain.mongo.BaseTimeDocument;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "answer")
public class AnswerDocument extends BaseTimeDocument {

    @Id
    private String id;

    private Long userId;

    private String content;

    private Long likeCount;

    private boolean isBlind;

    private boolean isSelected;

}
