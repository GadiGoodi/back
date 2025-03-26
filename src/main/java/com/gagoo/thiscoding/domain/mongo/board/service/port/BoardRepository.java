package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board getById(String qnaId);
    List<Long> getTop10Users();
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByUserId(Long userId, Pageable pageable);
    Page<Board> findByUserIdAndParentIdIsNotRoot(Long userId, Pageable pageable);
    Page<Search> findByTitleOrContent(String title, String content, Pageable pageable);
    Page<Board> findAnswerByQnaId(String qnaId, Pageable pageable);
    Page<Board> findAnswerByQnaIdSortByIsSelected(String qnaId, Pageable pageable);
    boolean existsById(String qnaId);
    boolean existsByParentIdAndIsSelectedIsTrue(String qnaId);
    void incrementViewCount(String qnaId);
    void incrementAnswerCount(String parentQnaId);
    void incrementReplyCount(String qnaId);
    void incrementLikeCount(String qnaId);
    void decrementLikeCount(String qnaId);
    void adoptAnswer(String qnaId);
}