package com.gagoo.thiscoding.domain.mongo.answer.service.port;

import java.util.List;

public interface AnswerCustomRepository {
    List<Long> getTop10Users();
}
