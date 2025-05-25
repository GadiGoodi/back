package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "qna_stats")
public class BoardStatsDocument {
    @Id
    private String qnaId;
    private Long likeCount;
    private Long viewCount;
    private Long answerCount;
    private Long replyCount;
    private LocalDateTime lastUpdated;

    public static BoardStatsDocument from(BoardStats stats) {
        BoardStatsDocument boardStatsDocument = new BoardStatsDocument();
        boardStatsDocument.qnaId = stats.getQnaId();
        boardStatsDocument.likeCount = stats.getLikeCount();
        boardStatsDocument.viewCount = stats.getViewCount();
        boardStatsDocument.answerCount = stats.getAnswerCount();
        boardStatsDocument.replyCount = stats.getReplyCount();
        boardStatsDocument.lastUpdated = stats.getLastUpdated();
        return boardStatsDocument;
    }

    public BoardStats toModel() {
        return BoardStats.builder()
                .qnaId(qnaId)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .answerCount(answerCount)
                .replyCount(replyCount)
                .lastUpdated(lastUpdated)
                .build();
    }
}
