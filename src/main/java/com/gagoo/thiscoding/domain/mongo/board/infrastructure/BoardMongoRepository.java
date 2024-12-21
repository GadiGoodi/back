package com.gagoo.thiscoding.domain.mongo.board.infrastructure;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardMongoRepository extends MongoRepository<BoardDocument, String>, BoardCustomRepository {
    Page<BoardDocument> findByUserId(Long userId, Pageable pageable);
}

