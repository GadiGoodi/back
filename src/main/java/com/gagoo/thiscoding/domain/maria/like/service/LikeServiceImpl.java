package com.gagoo.thiscoding.domain.maria.like.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.LikeCountUnderFlowException;
import com.gagoo.thiscoding.domain.maria.like.controller.port.LikeService;
import com.gagoo.thiscoding.domain.maria.like.domain.Like;
import com.gagoo.thiscoding.domain.maria.like.service.exception.ExistLike;
import com.gagoo.thiscoding.domain.maria.like.service.exception.LikeNotFoundException;
import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardQueryRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsService;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final BoardStatsService boardStatsService;
    private final UserFinder userFinder;
    private final SecurityUtils securityUtils;

    /**
     * 답변 추천
     * @param qnaId
     * @return
     */
    @Override
    public Like likeAnswer(String qnaId) {
        validateQnaAndAnswer(qnaId);
        validateLikeExists(qnaId);

        User currentUser = getCurrentUser();

        Like like = Like.create(currentUser, qnaId);

        boardStatsService.incrementLikeCount(qnaId);
        return likeRepository.save(like);
    }

    /**
     * 답변 추천 취소
     * @param qnaId
     */
    @Override
    public void cancelAnswerLike(String qnaId) {
        validateQnaAndAnswer(qnaId);
        validateLikeCount(qnaId);

        Like like = getLikeByQnaIdAndUserId(qnaId, getCurrentUser().getId());

        boardStatsService.decrementLikeCount(qnaId);
        likeRepository.deleteById(like.getId());
    }

    /**
     * 원본 게시글 및 답변 존재, isBlind 여부 검증
     * @param qnaId
     */
    private void validateQnaAndAnswer(String qnaId) {
        Board board = getBoardById(qnaId);
        Board parentBoard = getBoardById(board.getParentId());

        validateIsBlind(board);
        validateIsBlind(parentBoard);
    }

    /**
     * 게시글 블라인드 여부 검증
     * @param board
     */
    private void validateIsBlind(Board board) {
        if(board.isBlind()) {
            throw new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND);
        }
    }

    /**
     * 답변 추천 여부 검증
     * @param qnaId
     */
    private void validateLikeExists(String qnaId) {
        if(likeRepository.existsByQnaIdAndUserId(qnaId, getCurrentUser().getId())) {
            throw new ExistLike(ErrorCode.ALREADY_LIKE);
        }
    }

    /**
     * 추천 개수 검증
     * @param qnaId
     */
    private void validateLikeCount(String qnaId) {
        if(boardStatsService.findBoardStats(qnaId).getLikeCount() == 0) {
            throw new LikeCountUnderFlowException(ErrorCode.INVALID_LIKE_COUNT);
        }
    }

    /**
     * 추천 조회
     * @param qnaId
     * @param userId
     * @return
     */
    private Like getLikeByQnaIdAndUserId(String qnaId, Long userId) {
        return likeRepository.findByQnaIdAndUserId(qnaId, userId).orElseThrow(
                () -> new LikeNotFoundException(ErrorCode.LIKE_NOT_FOUND)
        );
    }

    /**
     * Board 조회
     * @param qnaId
     * @return
     */
    private Board getBoardById(String qnaId) {
        return boardQueryRepository.getById(qnaId);
    }

    /**
     * 로그인 사용자 조회
     * @return
     */
    private User getCurrentUser() {
        return userFinder.getByEmail(securityUtils.getUserEmail());
    }
}
