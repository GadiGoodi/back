package com.gagoo.thiscoding.domain.mongo.answer.infrastructure;

import com.gagoo.thiscoding.domain.mongo.answer.domain.Answer;
import com.gagoo.thiscoding.domain.mongo.answer.service.port.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

    private final AnswerMongoRepository answerMongoRepository;

    public Answer save(Answer answer) {
        return answerMongoRepository.save(AnswerDocument.from(answer)).toModel();
    }
}
