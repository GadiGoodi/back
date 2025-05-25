package com.gagoo.thiscoding.domain.mongo.board.infrastructure.mongo;

import com.gagoo.thiscoding.domain.mongo.board.infrastructure.BoardStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardStatsMongoRepository extends MongoRepository<BoardStatsDocument, String> {
}
