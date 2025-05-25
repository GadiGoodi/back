package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsService;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class BoardStatsServiceImpl implements BoardStatsService {

    private final BoardStatsRepository boardStatsRepository;

    @Override
    public BoardStats findBoardStats(String qnaId) {
        return boardStatsRepository.findBoardStatsByQnaId(qnaId).orElseThrow(
                () -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND)
        );
    }

    @Override
    public BoardStats initQnaStats(String qnaId) {
        return boardStatsRepository.save(
                BoardStats.initQnaStats(qnaId)
        );
    }

    @Override
    public BoardStats initAnswerStats(String qnaId) {
        return boardStatsRepository.save(
                BoardStats.initAnswerStats(qnaId)
        );
    }

    @Override
    public Map<String, BoardStats> getStatsByBoardIds(List<String> boardIds) {
        return boardStatsRepository.getStatsByIds(boardIds);
    }

    @Override
    public void incrementViewCount(String qnaId) {
        boardStatsRepository.incrementViewCount(qnaId);
    }

    @Override
    public void incrementLikeCount(String qnaId) {
        boardStatsRepository.incrementLikeCount(qnaId);
    }

    @Override
    public void incrementAnswerCount(String qnaId) {
        boardStatsRepository.incrementAnswerCount(qnaId);
    }

    @Override
    public void incrementReplyCount(String qnaId) {
        boardStatsRepository.incrementReplyCount(qnaId);
    }

    @Override
    public void decrementLikeCount(String qnaId) {
        boardStatsRepository.decrementLikeCount(qnaId);
    }

    @Override
    public void decrementReplyCount(String qnaId) {
        boardStatsRepository.decrementReplyCount(qnaId);
    }

    @Override
    public void flushToMongo() {

    }
}
