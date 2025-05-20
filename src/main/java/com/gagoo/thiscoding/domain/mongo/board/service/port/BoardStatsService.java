package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;

import java.util.List;
import java.util.Map;

public interface BoardStatsService {
    BoardStats findBoardStats(String qnaId);
    BoardStats initQnaStats(String qnaId);
    BoardStats initAnswerStats(String qnaId);
    Map<String, BoardStats> getStatsByBoardIds(List<String> boardIds);
    void incrementViewCount(String qnaId);
    void incrementLikeCount(String qnaId);
    void incrementAnswerCount(String qnaId);
    void incrementReplyCount(String qnaId);
    void decrementLikeCount(String qnaId);
    void decrementReplyCount(String qnaId);
    void flushToMongo(); // Redis → Mongo 동기화
}
