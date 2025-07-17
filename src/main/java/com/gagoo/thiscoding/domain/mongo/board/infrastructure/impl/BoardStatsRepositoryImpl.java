package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardStatsDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardStatsMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis.BoardStatsCommandCache;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis.BoardStatsQueryCache;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardStatsRepositoryImpl implements BoardStatsRepository {

    private final BoardStatsMongoRepository boardStatsMongoRepository;
    private final BoardStatsQueryCache boardStatsQueryCache;
    private final BoardStatsCommandCache boardStatsCommandCache;

    /**
     * qna 카운트 필드 조회
     */
    @Override
    public Optional<BoardStats> findBoardStatsByQnaId(String qnaId) {
        return boardStatsMongoRepository.findById(qnaId).map(BoardStatsDocument::toModel);
    }

    @Override
    public Map<String, BoardStats> getStatsByIds(List<String> qnaIds) {
        return boardStatsQueryCache.getWithFallback(
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
        boardStatsCommandCache.incrementAnswerCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void incrementReplyCount(String boardId) {
        boardStatsCommandCache.incrementReplyCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void incrementLikeCount(String boardId) {
        boardStatsCommandCache.incrementLikeCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void incrementViewCount(String boardId) {
        boardStatsCommandCache.incrementViewCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void decrementLikeCount(String boardId) {
        boardStatsCommandCache.decrementLikeCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void decrementReplyCount(String boardId) {
        boardStatsCommandCache.decrementReplyCount(
                boardId,
                this::findBoardStatsByQnaId
        );
    }

    @Override
    public void flushCache() {
        boardStatsCommandCache.flushCache(this::save);
    }
}