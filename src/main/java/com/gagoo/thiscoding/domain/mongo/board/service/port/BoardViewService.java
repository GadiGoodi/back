package com.gagoo.thiscoding.domain.mongo.board.service.port;


public interface BoardViewService {
    void processVisit(String qnaId, String visitorId);
}
