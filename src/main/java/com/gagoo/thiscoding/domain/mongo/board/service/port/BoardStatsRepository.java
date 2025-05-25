package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BoardStatsRepository {
    // 조회 관련 메서드 (Query)
    Optional<BoardStats> findBoardStatsByQnaId(String qnaId);

    // 캐시 관련 메서드 (기존 BoardStatsCachePort에서 이전)
    Map<String, BoardStats> getStatsByIds(List<String> qnaIds);

    // 명령 관련 메서드 (Command)
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
