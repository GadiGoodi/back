package com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo;

import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeMongoRepository extends MongoRepository<CodeDocument, String> {

}
