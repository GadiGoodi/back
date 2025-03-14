package com.gagoo.thiscoding.domain.maria.like.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.like.controller.port.LikeService;
import com.gagoo.thiscoding.domain.maria.like.domain.Like;
import com.gagoo.thiscoding.domain.maria.like.service.exception.ExistLike;
import com.gagoo.thiscoding.domain.maria.like.service.exception.LikeNotFoundException;
import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
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

        User user = getCurrentUser();

        Like like = Like.create(user, qnaId);

        return likeRepository.save(like);
    }

    /**
     * 답변 추천 취소
     * @param qnaId
     */
    @Override
    public void cancelAnswerLike(String qnaId) {
        validateQnaAndAnswer(qnaId);

        Like like = getLikeByQnaIdAndUserId(qnaId, getCurrentUser().getId());

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
     * 답변 조회
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
        return boardRepository.getById(qnaId);
    }

    /**
     * 로그인 사용자 조회
     * @return
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }
}
