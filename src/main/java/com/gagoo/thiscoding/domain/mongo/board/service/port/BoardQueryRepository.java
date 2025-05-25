package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.KeywordSearchResult;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageAnswer;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageBookmarkQuestion;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.MyPageQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    Board getById(String qnaId);
    List<Long> getTop10Users();
    Page<Board> findAll(Pageable pageable);
    Page<MyPageQuestion> findQuestionsByUserId(Long userId, Pageable pageable);
    Page<MyPageAnswer> findAnswerByUserId(Long userId, Pageable pageable);
    Page<KeywordSearchResult> findByKeyword(String keyword, Pageable pageable);
    Page<Board> findAnswerByQnaId(String qnaId, Pageable pageable);
    Page<MyPageBookmarkQuestion> findBookmarkedQuestionByIdIn(List<String> qnaId, Pageable pageable);
    boolean existsById(String qnaId);
    boolean existsByParentIdAndIsSelectedIsTrue(String qnaId);

    Page<Board> findRootQuestions(Pageable pageable);

}
