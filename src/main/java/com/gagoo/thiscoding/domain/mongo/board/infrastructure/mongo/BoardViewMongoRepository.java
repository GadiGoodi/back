package com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo;

import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardViewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;

public interface BoardViewMongoRepository extends MongoRepository<BoardViewDocument, String> {
    @Query(value = "{ 'qnaId': ?0, 'visitorId': ?1, 'viewDate': ?2 }", exists = true)
    boolean existsByQnaAndVisitorToday(String qnaId, String visitorId, LocalDate today);
}
