package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardView;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardViewDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardViewMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class BoardViewRepositoryImpl implements BoardViewRepository {

    private final BoardViewMongoRepository boardViewMongoRepository;

    @Override
    public BoardView save(BoardView boardView) {
        return boardViewMongoRepository.save(BoardViewDocument.from(boardView)).toModel();
    }

    @Override
    public boolean existsByQnaAndVisitor(String qnaId, String visitorId) {
        return boardViewMongoRepository.existsByQnaAndVisitorToday(qnaId, visitorId, LocalDate.now());
    }
}
