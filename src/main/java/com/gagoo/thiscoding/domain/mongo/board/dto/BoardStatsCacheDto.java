package com.gagoo.thiscoding.domain.mongo.board.dto;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardStatsCacheDto {
    private String qnaId;
    private Long likeCount;
    private Long viewCount;
    private Long answerCount;
    private Long replyCount;
    private String lastUpdated;

    public static BoardStatsCacheDto from(BoardStats stats) {
        return new BoardStatsCacheDto(
                stats.getQnaId(),
                stats.getLikeCount(),
                stats.getViewCount(),
                stats.getAnswerCount(),
                stats.getReplyCount(),
                stats.getLastUpdated().toString()
        );
    }

    public BoardStats toModel() {
        return BoardStats.builder()
                .qnaId(qnaId)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .answerCount(answerCount)
                .replyCount(replyCount)
                .lastUpdated(LocalDateTime.parse(lastUpdated))
                .build();
    }
}
