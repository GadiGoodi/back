package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.KeywordSearchResult;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswer;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookmarkQuestion;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQuestion;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardQueryRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final BoardMongoRepository boardMongoRepository;
    private final BoardCustomRepository boardCustomRepository;

    @Override
    public Board getById(String qnaId) {
        return findById(qnaId).orElseThrow(
                () -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND));
    }

    public Optional<Board> findById(String qnaId) {
        return boardMongoRepository.findById(qnaId).map(BoardDocument::toModel);
    }

    /**
     * top10 유저 확인
     */
    @Override
    public List<Long> getTop10Users() {
        return boardCustomRepository.getTop10Users();
    }

    /**
     * qna 전체 조회
     */
    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardMongoRepository.findByParentIdAndIsBlindFalseOrderByCreateDateDesc("root", pageable)
                .map(BoardDocument::toModel);
    }

    /**
     * 내가 작성한 질문 조회
     */
    @Override
    public Page<MyPageQuestion> findQuestionsByUserId(Long userId, Pageable pageable) {
        return boardCustomRepository.findQuestionsByUserId(userId, pageable);
    }

    /**
     * 내가 작성한 답변 조회
     */
    @Override
    public Page<MyPageAnswer> findAnswerByUserId(Long userId, Pageable pageable) {
        return boardCustomRepository.findAnswerByUserId(userId, pageable);
    }

    /**
     * 내가 북마크한 질문 조회
     */
    @Override
    public Page<MyPageBookmarkQuestion> findBookmarkedQuestionByIdIn(List<String> bookmarkedQuestionList, Pageable pageable) {
        return boardCustomRepository.findBookmarkedQuestionByIdIn(bookmarkedQuestionList, pageable);
    }

    /**
     * 키워드 검색
     * - 제목
     * - 내용
     */
    @Override
    public Page<KeywordSearchResult> findByKeyword(String keyword, Pageable pageable) {
        return boardCustomRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Board> findAnswerByQnaId(String qnaId, Pageable pageable) {
        return boardCustomRepository.getAnswerByQnaIdSortByIsSelected(qnaId, pageable)
                .map(BoardDocument::toModel);
    }

    @Override
    public boolean existsById(String qnaId) {
        return boardMongoRepository.existsById(qnaId);
    }

    @Override
    public boolean existsByParentIdAndIsSelectedIsTrue(String parentId) {
        return boardMongoRepository.existsByParentIdAndIsSelectedTrue(parentId);
    }

    @Override
    public Page<Board> findRootQuestions(Pageable pageable) {
        return boardCustomRepository.findRootQuestions(pageable).map(BoardDocument::toModel);
    }
}
