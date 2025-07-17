package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface BoardStatsRepository {
    Optional<BoardStats> findBoardStatsByQnaId(String qnaId);
    Map<String, BoardStats> getStatsByIds(List<String> qnaIds);

    BoardStats save(BoardStats boardStats);
    void incrementAnswerCount(String boardId);
    void incrementReplyCount(String boardId);
    void incrementLikeCount(String boardId);
    void incrementViewCount(String boardId);
    void decrementLikeCount(String boardId);
    void decrementReplyCount(String boardId);

    // 캐시 동기화 메서드 (Redis에서 MongoDB로)
    void flushCache();
}
