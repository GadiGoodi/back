package com.gagoo.thiscoding.domain.mongo.board.service.port;

import java.util.List;

public interface BoardCustomRepository {
    List<Long> getTop10Users();
}
