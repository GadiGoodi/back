package com.gagoo.thiscoding.domain.mongo.answer.controller.port;

import com.gagoo.thiscoding.domain.mongo.answer.domain.Answer;
import com.gagoo.thiscoding.domain.mongo.answer.domain.dto.AnswerCreate;

public interface AnswerService {
    Answer create(AnswerCreate answerCreate);
}
