package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepository {
    Page<Board> findByUserId(Long userId, Pageable pageable);
    Page<Search> findByTitleOrContent(String title, String content, Pageable pageable);
    Page<Board> findByParentIdIsNull(Pageable pageable);
    Board save(Board board);
    boolean existsById(String qnaId);
}
