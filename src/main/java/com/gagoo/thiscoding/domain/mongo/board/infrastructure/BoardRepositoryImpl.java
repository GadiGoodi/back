package com.gagoo.thiscoding.domain.mongo.board.infrastructure;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
private final BoardMongoRepository boardMongoRepository;
    @Override
    public Page<Board> findByUserId(Long userId, Pageable pageable) {
        return boardMongoRepository.findByUserId(userId, pageable)
                .map(BoardDocument::toModel);
    }

    @Override
    public Board save(Board board) {
        return boardMongoRepository.save(BoardDocument.from(board)).toModel();
    }
}