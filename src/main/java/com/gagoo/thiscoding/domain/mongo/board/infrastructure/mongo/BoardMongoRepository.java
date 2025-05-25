package com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo;
import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoardMongoRepository extends MongoRepository<BoardDocument, String> {
    Page<BoardDocument> findByParentIdAndIsBlindFalseOrderByCreateDateDesc(String parentId, Pageable pageable);
    boolean existsByParentIdAndIsSelectedTrue(String parentId);
}

