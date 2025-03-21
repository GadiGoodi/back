package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.*;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.ExistAdoptedAnswer;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.InvalidAnswerUserException;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.NotQnaUserException;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewService;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;
    private final BoardViewService boardViewService;
    private final SecurityUtils securityUtils;

    /**
     * qna 등록
     */
    @Override
    @Transactional
    public Board create(BoardCreate boardCreate) {
        User currentUser = getCurrentUser();
        Board board = Board.create(currentUser, boardCreate);

        return boardRepository.save(board);
    }

    /**
     * qna 답변 작성
     */
    @Override
    public Board writeAnswer(String parentQnaId, BoardAnswer boardAnswer) {
        User currentUser = getCurrentUser();
        validateParentQnaExists(parentQnaId);

        Board answer = Board.writeAnswer(currentUser, parentQnaId, boardAnswer.content());
        boardRepository.incrementAnswerCount(parentQnaId);

        return boardRepository.save(answer);
    }

    /**
     * qna 상세조회
     */
    @Override
    public QnaDetail get(String qnaId, String visitorId) {
        boardViewService.processVisit(qnaId, visitorId);

        Board qnaDetail = boardRepository.getById(qnaId);
        Long replyCount = replyRepository.countByQnaId(qnaId);

        return QnaDetail.from(qnaDetail, replyCount);
    }

    /**
     * QnA 제목 + 내용 검색
     */
    @Override
    public Page<Search> searchByKeyword(String keyword, Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), PageSize.QNA);
        return boardRepository.findByTitleOrContent(keyword, keyword, customPageable);
    }

    /**
     * 마이페이지 내가 작성한 QnA 질문 조회
     */
    @Override
    public Page<MyPageQnAList> getMyPagePostQnA(Pageable pageable) {
        User currentUser = getCurrentUser();
        return boardRepository.findByUserId(currentUser.getId(),pageable)
                .map(MyPageQnAList::of);
    }

    /**
     * 마이페이지 내가 작성한 QnA 답변 조회
     */
    @Override
    public Page<MyPageAnswerList> getMyPagePostAnswer(Pageable pageable) {
        User currentUser = getCurrentUser();
        return boardRepository.findByUserIdAndParentIdIsNotRoot(currentUser.getId(),pageable)
                .map(MyPageAnswerList::of);
    }

    /**
     * 게시판 전체목록 조회
     */
    @Override
    public CustomPageDto<QnaList> findAll(Pageable pageable) {
        Page<QnaList> qnaListPage = boardRepository.findAll(pageable).map(
                QnaList::from
        );

        return CustomPageDto.of(qnaListPage);
    }

    /**
     * 특정 qna에 대한 답변 조회
     */
    @Override
    public Page<AnswerList> findAnswersByQnaId(String qnaId, Pageable pageable) {
        Long currentUserId = getCurrentUser().getId();

        return boardRepository.findAnswerByQnaId(qnaId, pageable)
                .map(board -> {
                    boolean isLike = getIsLikeByQnaIdAndUserId(board.getId(), currentUserId);
                    return AnswerList.from(board, isLike);
                });
    }

    /**
     * 답변 채택
     */
    @Override
    public void adoptAnswer(String qnaId) {
        validateParentQnaExists(qnaId);

        Board currentAnswer = boardRepository.getById(qnaId);
        Long parentUserId = boardRepository.getById(currentAnswer.getParentId()).getUserId();

        User currentUser = getCurrentUser();

        validateParentAndCurrentUserIsEqual(parentUserId, currentUser.getId());
        validateParentAndAnswerUserIsEqual(parentUserId, currentAnswer.getUserId());
        validateIsSelectedAnswerExists(currentAnswer.getParentId());

        boardRepository.adoptAnswer(qnaId);
    }

    /**
     * 부모 게시물이 있는지 확인
     */
    private void validateParentQnaExists(String parentQnaId) {
        if (!boardRepository.existsById(parentQnaId)) {
            throw new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND);
        }
    }

    /**
     * 현재 로그인한 사용자 조회
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }

    /**
     * 좋아요 여부 조회
     */
    private boolean getIsLikeByQnaIdAndUserId(String qnaId, Long userId) {
        return likeRepository.findByQnaIdAndUserId(qnaId, userId).isPresent();
    }

    /**
     * 부모글 작성자 및 로그인 사용자 동일 여부 검증 (로그인한 사용자가 질문자인지)
     */
    private void validateParentAndCurrentUserIsEqual(Long parentUserId, Long currentUserId) {
        if(!(parentUserId.equals(currentUserId))) {
            throw new NotQnaUserException(ErrorCode.NOT_QNA_USER);
        }
    }

    /**
     * 부모글 작성자 및 답변 작성자 동일 여부 검증 (질문자가 본인 답변에 채택하는지)
     */
    private void validateParentAndAnswerUserIsEqual(Long parentUserId, Long answerUserId) {
        if(parentUserId.equals(answerUserId)) {
            throw new InvalidAnswerUserException(ErrorCode.INVALID_ANSWER_USER);
        }
    }

    /**
     * 이미 채택된 답변이 존재하는지 검증
     */
    private void validateIsSelectedAnswerExists(String parentId) {
        if(boardRepository.existsByParentIdAndIsSelectedIsTrue(parentId)) {
            throw new ExistAdoptedAnswer(ErrorCode.EXIST_ADOPTED_ANSWER);
        }
    }
}
