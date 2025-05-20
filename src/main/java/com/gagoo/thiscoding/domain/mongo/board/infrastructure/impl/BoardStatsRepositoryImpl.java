package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardStatsDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl.helper.BoardStatsUpdater;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardStatsMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis.BoardStatsRedisCache;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * BoardStatsRepository의 Redis 및 MongoDB 구현체
 * - Redis는 캐시로 사용
 * - MongoDB는 영구 저장소로 사용
 */
@Repository
@RequiredArgsConstructor
public class BoardStatsRepositoryImpl implements BoardStatsRepository {

    private final BoardStatsUpdater boardStatsUpdater;
    private final BoardStatsMongoRepository boardStatsMongoRepository;
    private final BoardStatsRedisCache boardStatsRedisCache;

    /**
     * qna 카운트 필드 조회
     */
    @Override
    public Optional<BoardStats> findBoardStatsByQnaId(String qnaId) {
        return boardStatsMongoRepository.findById(qnaId).map(BoardStatsDocument::toModel);
    }

    @Override
    public Map<String, BoardStats> getStatsByIds(List<String> qnaIds) {
        return boardStatsRedisCache.getWithFallback(
                qnaIds,
                missedIds ->
                boardStatsMongoRepository.findAllById(missedIds).stream()
                        .map(BoardStatsDocument::toModel)
                        .toList()
        );
    }

    @Override
    public BoardStats save(BoardStats boardStats) {
        return boardStatsMongoRepository.save(BoardStatsDocument.from(boardStats)).toModel();
    }

    @Override
    public void incrementAnswerCount(String boardId) {
        boardStatsUpdater.incrementAnswerCount(boardId);
    }

    @Override
    public void incrementReplyCount(String boardId) {
        boardStatsUpdater.incrementReplyCount(boardId);
    }

    @Override
    public void incrementLikeCount(String boardId) {
        boardStatsUpdater.incrementLikeCount(boardId);
    }

    @Override
    public void incrementViewCount(String boardId) {
        boardStatsUpdater.incrementViewCount(boardId);
    }

    @Override
    public void decrementLikeCount(String boardId) {
        boardStatsUpdater.decrementLikeCount(boardId);
    }

    @Override
    public void decrementReplyCount(String boardId) {
        boardStatsUpdater.decrementReplyCount(boardId);
    }

    @Override
    public void flushCache() {
    }
}