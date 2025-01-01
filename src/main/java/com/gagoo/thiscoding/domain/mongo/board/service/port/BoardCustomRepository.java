package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<BoardDocument> searchByKeyword(String keyword, Pageable pageable);
}
