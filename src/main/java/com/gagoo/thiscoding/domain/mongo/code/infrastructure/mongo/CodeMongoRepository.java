package com.gagoo.thiscoding.domain.mongo.code.infrastructure.mongo;

import com.gagoo.thiscoding.domain.mongo.code.infrastructure.CodeDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CodeMongoRepository extends ReactiveMongoRepository<CodeDocument, String> {

}
