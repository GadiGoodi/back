package com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo;

import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CodeMongoRepository extends MongoRepository<CodeDocument, String> {
    boolean existsByRoomIdAndFileName(Long roomId, String fileName);
    Optional<CodeDocument> findByRoomIdAndFileName(Long roomId, String fileName);
}
