package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.bookmark.service.port.BookmarkRepository;
import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardQueryService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.*;
import com.gagoo.thiscoding.domain.mongo.board.service.port.*;
import com.gagoo.thiscoding.global.paging.PageSize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardQueryRepository boardQueryRepository;
    private final BookmarkRepository bookmarkRepository;
    private final LikeRepository likeRepository;
    private final BoardViewService boardViewService;
    private final SecurityUtils securityUtils;
    private final UserFinder userFinder;
    private final BoardStatsService boardStatsService;

    /**
     * qna 상세조회
     */
    @Override
    public QnaDetail get(String qnaId, String visitorId) {
        System.out.println("qnaId = " + qnaId);
        boardViewService.processVisit(qnaId, visitorId);
        Board board = boardQueryRepository.getById(qnaId);
        System.out.println("board.getId() = " + board.getId());
        BoardStats boardStats = boardStatsService.findBoardStats(board.getId());
        boolean bookmarked = false;

        if (securityUtils.isLogin()) {
            Long userId = getCurrentUser().getId();
            bookmarked = bookmarkRepository.existsByQnaIdAndUserId(qnaId, userId);
        }

        return QnaDetail.from(board, boardStats, bookmarked);
    }

    /**
     * 키워드 검색
     * - 제목
     * - 내용
     */
    @Override
    public Page<KeywordSearchResult> searchByKeyword(String keyword, Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), PageSize.QNA);
        return boardQueryRepository.findByKeyword(keyword, customPageable);
    }

    /**
     * 내가 작성한 질문 조회
     */
    @Override
    public Page<MyPageQuestion> getMyPagePostQnA(Pageable pageable) {
        Long userId = getCurrentUser().getId();
        return boardQueryRepository.findQuestionsByUserId(userId, pageable);
    }

    /**
     * 내가 작성한 답변 조회
     */
    @Override
    public Page<MyPageAnswer> getMyPagePostAnswer(Pageable pageable) {
        Long userId = getCurrentUser().getId();
        return boardQueryRepository.findAnswerByUserId(userId, pageable);
    }

    /**
     * 내가 북마크한 질문 조회
     */
    @Override
    public Page<MyPageBookmarkQuestion> getMyPageBookMarkQuestion(Pageable pageable) {
        Long userId = getCurrentUser().getId();
        List<String> bookmarkedIds = bookmarkRepository.findQnaIdsByUserIdPaged(userId, pageable);

        return boardQueryRepository.findBookmarkedQuestionByIdIn(bookmarkedIds, pageable);
    }

    /**
     * qna 전체 조회
     */
    @Override
    public Page<QnA> findAll(Pageable pageable) {
        Page<Board> boards = boardQueryRepository.findRootQuestions(pageable);

        List<String> qnaIds = boards.stream().map(Board::getId).toList();

        Map<String, BoardStats> statsMap = boardStatsService.getStatsByBoardIds(qnaIds);

        return boards.map(board -> {
            BoardStats stats = statsMap.get(board.getId());
            return QnA.from(board, stats);
        });
    }

    /**
     * qna에 작성된 답변 조회
     */
    @Override
    public Page<Answer> findAnswersByQnaId(String qnaId, Pageable pageable) {
        Page<Board> answers = boardQueryRepository.findAnswerByQnaId(qnaId, pageable);
        Map<String, BoardStats> statsByBoardIds = boardStatsService.getStatsByBoardIds(answers.stream().map(Board::getId).toList());

        if (securityUtils.isLogin()) {
            Long userId = getCurrentUser().getId();
            List<String> likedIds = likeRepository.findQnaIdsByQnaIdsAndUserId(
                    answers.stream().map(Board::getId).toList(), userId);

            return answers.map(board -> {
                BoardStats stats = statsByBoardIds.get(board.getId());
                return Answer.of(board, stats, likedIds.contains(board.getId()));
            });
        }

        return answers.map(board -> {
            BoardStats stats = statsByBoardIds.get(board.getId());
            return Answer.of(board, stats, false);
        });
    }

    private User getCurrentUser() {
        return userFinder.getByEmail(securityUtils.getUserEmail());
    }
}
