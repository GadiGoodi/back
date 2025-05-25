package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.BaseTimeDocument;
import com.gagoo.thiscoding.domain.mongo.board.domain.BoardView;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Document(collection = "qna_view")
@CompoundIndexes({
        @CompoundIndex(name = "qna_visitor_date_idx",
                def = "{'qnaId': 1, 'visitorId': 1, 'viewDate': 1}",
                unique = true)
})
public class BoardViewDocument extends BaseTimeDocument {

    @Id
    private String id;
    private String qnaId;
    private String visitorId;
    private LocalDate viewDate;

    public static BoardViewDocument from(BoardView boardView) {
        BoardViewDocument boardViewDocument = new BoardViewDocument();
        boardViewDocument.id = boardView.getId();
        boardViewDocument.qnaId = boardView.getQnaId();
        boardViewDocument.visitorId = boardView.getVisitorId();
        boardViewDocument.viewDate = boardView.getViewDate();
        boardViewDocument.createDate = boardView.getCreateDate();

        return boardViewDocument;
    }

    public BoardView toModel() {
        return BoardView.builder()
                .id(id)
                .qnaId(qnaId)
                .visitorId(visitorId)
                .viewDate(viewDate)
                .createDate(createDate)
                .build();
    }
}
