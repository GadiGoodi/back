package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.maria.BaseTimeEntity;
import com.gagoo.thiscoding.domain.mongo.BaseTimeDocument;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "qna")
public class BoardDocument extends BaseTimeDocument {

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

    public static BoardDocument from(Board board) {
        BoardDocument boardDocument = new BoardDocument();
        boardDocument.id = board.getId();
        boardDocument.userId = board.getUserId();
        boardDocument.title = board.getTitle();
        boardDocument.content = board.getContent();
        boardDocument.language = board.getLanguage();
        boardDocument.parentId = board.getParentId();
        boardDocument.likeCount = board.getLikeCount();
        boardDocument.viewCount = board.getViewCount();
        boardDocument.answerCount = board.getAnswerCount();
        boardDocument.isBlind = board.isBlind();
        boardDocument.isSelected = board.isSelected();

        return boardDocument;
    }

    public Board toModel() {
        return Board.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .content(content)
                .language(language)
                .parentId(parentId)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .answerCount(answerCount)
                .isBlind(isBlind)
                .isSelected(isSelected)
                .build();
    }
}
