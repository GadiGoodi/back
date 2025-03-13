package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.controller.request.BoardAnswer;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.AnswerList;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaDetail;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Board create(BoardCreate boardCreate);
    QnaDetail get(String qnaId, String visitorId);
    Board writeAnswer(String qnaId, BoardAnswer boardAnswer);
    Page<Search> searchByKeyword(String keyword, Pageable pageable);
    Page<Board> getMyPagePostQnA(Pageable pageable);
    CustomPageDto<QnaList> findAll(Pageable pageable);
    Page<AnswerList> findAnswersByQnaId(String qnaId, Pageable pageable);
}
