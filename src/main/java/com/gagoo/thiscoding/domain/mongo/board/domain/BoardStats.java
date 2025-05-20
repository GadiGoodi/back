package com.gagoo.thiscoding.domain.mongo.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardStats {
    private String qnaId;
    private Long viewCount;
    private Long answerCount;
    private Long likeCount;
    private Long replyCount;
    private LocalDateTime lastUpdated;


    public static BoardStats initQnaStats(String qnaId) {
        return BoardStats.builder()
                .qnaId(qnaId)
                .viewCount(0L)
                .answerCount(0L)
                .replyCount(0L)
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    public static BoardStats initAnswerStats(String qnaId) {
        return BoardStats.builder()
                .qnaId(qnaId)
                .likeCount(0L)
                .replyCount(0L)
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
