package com.gagoo.thiscoding.domain.mongo.answer.service.port;

import com.gagoo.thiscoding.domain.mongo.answer.domain.Answer;

public interface AnswerRepository {

    Answer save(Answer answer);
}
