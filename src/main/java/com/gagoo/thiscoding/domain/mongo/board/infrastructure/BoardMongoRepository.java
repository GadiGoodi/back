package com.gagoo.thiscoding.domain.mongo.board.infrastructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardMongoRepository extends MongoRepository<BoardDocument, String> {
    Page<BoardDocument> findByUserId(Long userId, Pageable pageable);
}

