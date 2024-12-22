package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import org.springframework.data.domain.Page;

public interface BoardService {
    Board create(BoardCreate boardCreate);
    Page<Board> search(String keyword, int page);
}
