package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;

public interface BoardCommandRepository {
    Board save(Board board);
    void markParentAsAdopted(String qnaId);
    void adoptAnswer(String qnaId);
}
