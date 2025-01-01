package com.gagoo.thiscoding.domain.mongo.answer.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerMongoRepository extends MongoRepository<AnswerDocument, String> {
}
