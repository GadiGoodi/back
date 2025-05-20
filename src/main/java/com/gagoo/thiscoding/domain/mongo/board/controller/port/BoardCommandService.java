package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;

public interface BoardCommandService {
    Board create(BoardCreate boardCreate);
    Board writeAnswer(String qnaId, BoardAnswer boardAnswer);
    void adoptAnswer(String qnaId);
}
