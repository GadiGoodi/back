package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.BaseTimeDocument;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "qna")
public class BoardDocument extends BaseTimeDocument {

    @Id
    private String id;

    private Long userId;

    private String title;

    private String content;

    private String language;

    private Long viewCount;

    private Long answerCount;

    private boolean isBlind;

    public static BoardDocument from(Board board) {
        BoardDocument boardDocument = new BoardDocument();
        boardDocument.id = board.getId();
        boardDocument.userId = board.getUserId();
        boardDocument.title = board.getTitle();
        boardDocument.content = board.getContent();
        boardDocument.language = board.getLanguage();
        boardDocument.viewCount = board.getViewCount();
        boardDocument.answerCount = board.getAnswerCount();
        boardDocument.isBlind = board.isBlind();

        return boardDocument;
    }

    public Board toModel() {
        return Board.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .content(content)
                .language(language)
                .viewCount(viewCount)
                .answerCount(answerCount)
                .isBlind(isBlind)
                .build();
    }
}
