package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardView;

public interface BoardViewRepository {
    BoardView save(BoardView boardView);

    boolean existsByQnaAndVisitor(String id, String visitorId);
}
