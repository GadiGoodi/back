package com.gagoo.thiscoding.domain.mongo.board.service;

import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.BoardCreate;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCustomRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.paging.PagingProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardCustomRepository boardCustomRepository;

    /**
     * qna 등록
     */
    @Override
    public Board create(BoardCreate boardCreate) {
        Board board = Board.create(boardCreate);

        return boardRepository.save(board);
    }

    /**
     * QnA 제목 + 내용 검색
     */
    @Override
    public Page<Board> search(String keyword, int page) {
        Pageable pageable = PagingProcessor.getPageable(page, 10);
        return boardCustomRepository.searchByKeyword(keyword, pageable)
            .map(BoardDocument::toModel);
    }
}
