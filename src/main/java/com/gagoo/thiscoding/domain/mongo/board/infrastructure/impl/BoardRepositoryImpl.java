package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMongoRepository boardMongoRepository;
    private final BoardCustomRepository boardCustomRepository;

    @Override
    public Board getById(String qnaId) {
        return findById(qnaId).orElseThrow(
                () -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND)
        );
    }

    /**
     * 조회수 증가
     */
    @Override
    public void incrementViewCount(String qnaId) {
        boardCustomRepository.incrementViewCount(qnaId);
    }

    /**
     * 댓글 갯수 증가
     */
    @Override
    public void incrementReplyCount(String qnaId) {
        boardCustomRepository.incrementReplyCount(qnaId);
    }

    /**
     * 답변 갯수 증가
     */
    @Override
    public void incrementAnswerCount(String parentQnaId) {
        boardCustomRepository.incrementAnswerCount(parentQnaId);
    }

    /**
     * 채택 많이 된 상위 10명 조회
     */
    @Override
    public List<Long> getTop10Users() {
        return boardCustomRepository.getTop10Users();
    }

    /**
     * qna 전체조회
     */
    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardMongoRepository.findByParentIdAndIsBlindFalseOrderByCreateDateDesc("root", pageable)
                .map(BoardDocument::toModel);
    }

    @Override
    public Page<Board> findByUserId(Long userId,Pageable pageable) {
        return boardMongoRepository.findByUserIdAndParentIdOrderByCreateDateDesc(userId,pageable,"root")
                .map(BoardDocument::toModel);
    }

    @Override
    public Page<Board> findByUserIdAndParentIdIsNotRoot(Long userId, Pageable pageable) {
        return boardMongoRepository.findByUserIdAndAnswerCountIsNull(userId,pageable)
                .map(BoardDocument::toModel);
    }

    @Override
    public Page<Search> findByTitleOrContent(String title, String content, Pageable pageable) {
        return boardMongoRepository.findByTitleContainingOrContentContaining(title, content, pageable).map(
            Search::from);
    }

    @Override
    public Page<Board> findAnswerByQnaId(String qnaId, Pageable pageable) {
        return boardMongoRepository.findByParentIdAndIsBlindFalseOrderByCreateDateDesc(qnaId, pageable).map(BoardDocument::toModel);
    }

    @Override
    public Board save(Board board) {
        return boardMongoRepository.save(BoardDocument.from(board)).toModel();
    }

    @Override
    public boolean existsById(String qnaId) {
        return boardMongoRepository.existsById(qnaId);
    }

    public Optional<Board> findById(String qnaId) {
        return boardMongoRepository.findById(qnaId).map(BoardDocument::toModel);
    }
}