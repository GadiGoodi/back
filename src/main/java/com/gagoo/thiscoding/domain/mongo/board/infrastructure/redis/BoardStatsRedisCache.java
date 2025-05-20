package com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.dto.BoardStatsCacheDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BoardStatsRedisCache {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "qnaStats:";
    private static final long CACHE_TTL = 3600; // 1시간 (초)

    /**
     * Redis 캐시에서 QnA 통계 정보를 조회하고,
     * 캐시 미스 항목은 fallbackFn을 통해 조회한 뒤 재캐싱함.
     *
     * @param qnaIds     QnA ID 리스트
     * @param fallback 캐시 미스 시 호출할 DB 조회 함수
     * @return QnA ID -> BoardStats 매핑
     */
    public Map<String, BoardStats> getWithFallback(
            List<String> qnaIds,
            Function<List<String>, List<BoardStats>> fallback
    ) {
        List<String> redisKeys = qnaIds.stream()
                .map(id -> KEY_PREFIX + id)
                .toList();

        List<Object> cachedResults = redisTemplate.opsForValue().multiGet(redisKeys);

        Map<String, BoardStats> resultMap = new HashMap<>();
        List<String> missedQnaIds = new ArrayList<>();

        for (int i = 0; i < qnaIds.size(); i++) {
            Object cached = cachedResults.get(i);
            String qnaId = qnaIds.get(i);
            if (cached instanceof BoardStatsCacheDto statsCacheDto) {
                resultMap.put(qnaId, statsCacheDto.toModel());
            } else {
                missedQnaIds.add(qnaId);
            }
        }

        if (!missedQnaIds.isEmpty()) {
            List<BoardStats> fallbackStats = fallback.apply(missedQnaIds);
            for (BoardStats stats : fallbackStats) {
                String key = KEY_PREFIX + stats.getQnaId();
                BoardStatsCacheDto statsCacheDto = BoardStatsCacheDto.from(stats);
                redisTemplate.opsForValue().set(key, statsCacheDto, CACHE_TTL, TimeUnit.SECONDS);
                resultMap.put(stats.getQnaId(), stats);
            }
        }

        return resultMap;
    }
}
