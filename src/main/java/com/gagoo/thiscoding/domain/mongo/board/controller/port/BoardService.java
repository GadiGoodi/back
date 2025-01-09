package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.service.dto.QnaList;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Board create(BoardCreate boardCreate);
    Page<Search> searchByKeyword(String keyword, int page);
    Page<Board> getMyPagePostQnA(Pageable pageable);
    CustomPageDto<Page<QnaList>> findAll(Pageable pageable);
}
