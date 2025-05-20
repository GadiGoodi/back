package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardCommandRepositoryImpl implements BoardCommandRepository {

    private final BoardMongoRepository boardMongoRepository;
    private final BoardCustomRepository boardCustomRepository;

    @Override
    public Board save(Board board) {
        return boardMongoRepository.save(BoardDocument.from(board)).toModel();
    }

    @Override
    public void markParentAsAdopted(String qnaId) {
        boardCustomRepository.markParentAsAdopted(qnaId);
    }

    @Override
    public void adoptAnswer(String qnaId) {
        boardCustomRepository.adoptAnswer(qnaId);
    }
}
