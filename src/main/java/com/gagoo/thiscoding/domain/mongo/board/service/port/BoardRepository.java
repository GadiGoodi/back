package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board getById(String qnaId);
    boolean existsById(String qnaId);
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByUserId(Long userId, Pageable pageable);
    Page<Search> findByTitleOrContent(String title, String content, Pageable pageable);
    List<Board> saveAll(List<Board> boardList);
}
