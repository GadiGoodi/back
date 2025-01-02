package com.gagoo.thiscoding.domain.mongo.board.service.port;

import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    List<Long> getTop10Users();
    Page<BoardDocument> searchByKeyword(String keyword, Pageable pageable);
}