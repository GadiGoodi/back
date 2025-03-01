package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.Board;
import com.gagoo.thiscoding.domain.mongo.board.domain.dto.Search;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo.BoardMongoRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMongoRepository boardMongoRepository;

    @Override
    public Board getById(String qnaId) {
        return findById(qnaId).orElseThrow(
                () -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND)
        );
    }

    /**
     * qna 전체조회
     */
    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardMongoRepository.findByParentIdOrderByCreateDateDesc("root", pageable).map(BoardDocument::toModel);
    }

    @Override
    public Page<Board> findByUserId(Long userId, Pageable pageable) {
        return boardMongoRepository.findByUserId(userId, pageable)
                .map(BoardDocument::toModel);
    }

    @Override
    public Page<Search> findByTitleOrContent(String title, String content, Pageable pageable) {
        return boardMongoRepository.findByTitleContainingOrContentContaining(title, content, pageable).map(
            Search::from);
    }

    @Override
    public Board save(Board board) {
        return boardMongoRepository.save(BoardDocument.from(board)).toModel();
    }

    @Override
    public List<Board> saveAll(List<Board> boards) {
        return boardMongoRepository.saveAll(
                        boards.stream()
                                .map(BoardDocument::from)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(BoardDocument::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String qnaId) {
        return boardMongoRepository.existsById(qnaId);
    }

    public Optional<Board> findById(String qnaId) {
        return boardMongoRepository.findById(qnaId).map(BoardDocument::toModel);
    }
}