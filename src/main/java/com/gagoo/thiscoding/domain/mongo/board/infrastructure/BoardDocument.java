package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

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

    @Field(name = "users_id")
    private Long userId;

    private String nickname;

    private String profileImg;

    private String title;

    private String content;

    private String language;

    private String parentId;

    private boolean isBlind;

    private boolean isSelected;

    private boolean isAdopted;

    public static BoardDocument from(Board board) {
        BoardDocument boardDocument = new BoardDocument();
        boardDocument.id = board.getId();
        boardDocument.userId = board.getUserId();
        boardDocument.nickname = board.getNickname();
        boardDocument.profileImg = board.getProfileImg();
        boardDocument.title = board.getTitle();
        boardDocument.content = board.getContent();
        boardDocument.language = board.getLanguage();
        boardDocument.parentId = board.getParentId();
        boardDocument.isBlind = board.isBlind();
        boardDocument.isSelected = board.isSelected();
        boardDocument.isAdopted = board.isAdopted();

        return boardDocument;
    }

    public Board toModel() {
        return Board.builder()
                .id(id)
                .userId(userId)
                .nickname(nickname)
                .profileImg(profileImg)
                .title(title)
                .content(content)
                .language(language)
                .parentId(parentId)
                .isBlind(isBlind)
                .isSelected(isSelected)
                .isAdopted(isAdopted)
                .createDate(createDate)
                .build();
    }
}