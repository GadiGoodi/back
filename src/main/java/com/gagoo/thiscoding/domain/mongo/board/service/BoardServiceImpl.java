package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.AnswerList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewService;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.AuthorizationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final BoardViewService boardViewService;
    private final SecurityUtils securityUtils;
    private final HttpServletUtils httpServletUtils;
    private final UuidHolder uuidHolder;

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
    public QnaDetail get(String qnaId, HttpServletRequest request, HttpServletResponse response) {
        String visitorId = getVisitorId(request, response);

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
     * 마이페이지 내가 작성한 Qna 조회
     */
    @Override
    public Page<Board> getMyPagePostQnA(Pageable pageable) {
        User currentUser = getCurrentUser();
        return boardRepository.findByUserId(currentUser.getId(), pageable);
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
        return boardRepository.findAnswerByQnaId(qnaId, pageable).map(AnswerList::from);
    }

    /**
     * 방문자 정보 조회
     */
    private String getVisitorId(HttpServletRequest request, HttpServletResponse response) {
        try {
            return securityUtils.getUserEmail();
        } catch (AuthorizationException e) {
            // 비로그인 사용자의 경우 쿠키에서 visitorId 가져오기
            Optional<Cookie> visitorCookie = httpServletUtils.getCookie(request, "visitorId");

            if (visitorCookie.isPresent()) {
                return visitorCookie.get().getValue();
            }

            // 쿠키가 없으면 새로 생성
            String visitorId = uuidHolder.random();
            httpServletUtils.addCookie(response, "visitorId", visitorId, 60L * 60 * 24 * 365);

            return visitorId;
        }
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
}
