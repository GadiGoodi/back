package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Board create(BoardCreate boardCreate);
    Page<Search> searchByKeyword(String keyword, int page);
    Page<Board> getMyPagePostQnA(Pageable pageable);
}
