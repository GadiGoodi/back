package com.gagoo.thiscoding.domain.mongo.answer.infrastructure;

import com.gagoo.thiscoding.domain.mongo.BaseTimeDocument;
import com.gagoo.thiscoding.domain.mongo.answer.domain.Answer;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "answer")
public class AnswerDocument extends BaseTimeDocument {

    @Id
    private String id;

    private String boardId;

    private Long userId;

    private String content;

    private Long likeCount;

    private boolean isBlind;

    private boolean isSelected;

    public static AnswerDocument from(Answer answer) {
        AnswerDocument answerDocument = new AnswerDocument();
        answerDocument.id = answerDocument.getId();
        answerDocument.boardId = answer.getBoardId();
        answerDocument.userId = answer.getUserId();
        answerDocument.content = answer.getContent();
        answerDocument.likeCount = answer.getLikeCount();
        answerDocument.isBlind = answer.isBlind();
        answerDocument.isSelected = answer.isSelected();

        return answerDocument;
    }

    public Answer toModel() {
        return Answer.builder()
                .id(this.id)
                .boardId(this.boardId)
                .userId(this.userId)
                .content(this.content)
                .likeCount(this.likeCount)
                .isBlind(this.isBlind)
                .isSelected(this.isSelected)
                .build();
    }
}
