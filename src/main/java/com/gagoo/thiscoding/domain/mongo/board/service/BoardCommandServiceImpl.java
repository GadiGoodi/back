package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardCommandService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.ExistAdoptedAnswer;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCommandRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardQueryRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsService;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final BoardCommandRepository boardCommandRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final BoardStatsService boardStatsService;
    private final UserFinder userFinder;
    private final SecurityUtils securityUtils;

    /**
     * 질문 작성
     */
    @Override
    public Board create(BoardCreate boardCreate) {
        User currentUser = getCurrentUser();
        Board board = Board.write(currentUser, boardCreate);
        Board savedBoard = boardCommandRepository.save(board);

        boardStatsService.initQnaStats(savedBoard.getId());

        return savedBoard;
    }

    /**
     * 답변 작성
     */
    @Override
    public Board writeAnswer(String qnaId, BoardAnswer boardAnswer) {
        User currentUser = getCurrentUser();
        validateParentQnaExists(qnaId);

        Board answer = Board.writeAnswer(currentUser, qnaId, boardAnswer.content());
        Board savedAnswer = boardCommandRepository.save(answer);
        boardStatsService.incrementAnswerCount(qnaId);

        boardStatsService.initAnswerStats(savedAnswer.getId());

        return savedAnswer;
    }

    /**
     * 답변 채택
     */
    @Override
    public void adoptAnswer(String answerId) {
        validateAllAdopt(answerId);

        Board answer = boardQueryRepository.getById(answerId);
        boardCommandRepository.adoptAnswer(answer.getId());
        boardCommandRepository.markParentAsAdopted(answer.getParentId());
    }

    // 검증 메서드

    /**
     * 부모 게시물이 존재하는지 확인
     */
    private void validateParentQnaExists(String parentQnaId) {
        if (!boardQueryRepository.existsById(parentQnaId)) {
            throw new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND);
        }
    }

    /**
     * 채택할 때 필요한 유효성 검사
     * - 이미 채택되었는지 있는지 확인 -> 이미 채택됐으면 EXIST_ADOPTED_ANSWER
     * - 작성자 본인인지 확인 -> 본인이 아니면 NOT_QNA_USER
     * - 본인지 작성한 답변인지 확인 ->  본인이 작성했으면 INVALID_ANSWER_USER
     */
    private void validateAllAdopt(String answerId) {
        Board currentAnswer = boardQueryRepository.getById(answerId);
        Long parentUserId = currentAnswer.getUserId();
        User currentUser = getCurrentUser();

        if (boardQueryRepository.existsByParentIdAndIsSelectedIsTrue(answerId)) {
            throw new ExistAdoptedAnswer(ErrorCode.EXIST_ADOPTED_ANSWER);
        }

        currentAnswer.validateAdoptable(currentUser.getId(), parentUserId);
    }

    private User getCurrentUser() {
        return userFinder.getByEmail(securityUtils.getUserEmail());
    }

}
