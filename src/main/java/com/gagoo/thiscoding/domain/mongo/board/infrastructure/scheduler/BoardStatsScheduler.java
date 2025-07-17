package com.gagoo.thiscoding.domain.mongo.board.infrastructure.scheduler;

import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardStatsScheduler {

    private final BoardStatsRepository boardStatsRepository;

    @Scheduled(fixedDelay = 600000) // 10분마다 동기화
    public void flushToMongo() {
        boardStatsRepository.flushCache();
    }
}
