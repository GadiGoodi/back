package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.BaseTimeEntity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "qna")
public class BoardDocument extends BaseTimeEntity {

    @Id
    private String id;

    @Field(name = "user_id")
    private Long userId;

    private String title;

    private String content;

    private String language;

    @Field(name = "parent_id")
    private Long parentId;

    @Field(name = "like_count")
    private Long likeCount;

    @Field(name = "view_count")
    private Long viewCount;

    @Field(name = "answer_count")
    private Long answerCount;

    @Field(name = "is_blind")
    private boolean isBlind;

    @Field(name = "is_selected")
    private boolean isSelected;

}
