package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import org.springframework.data.domain.Page;

public interface BoardService {
    Board create(BoardCreate boardCreate);
    Page<Search> searchByKeyword(String keyword, int page);
}
