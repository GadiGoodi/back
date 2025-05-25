package com.gagoo.thiscoding.domain.mongo.board.infrastructure.impl;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BoardStatsCache   {
    private final RedisTemplate<String, BoardStats> redisTemplate;
    private static final String PREFIX = "board:stats:";

    public Map<String, BoardStats> getWithFallback(
            List<String> qnaIds,
            Function<List<String>, List<BoardStats>> fallbackFn
    ) {
        List<String> keys = qnaIds.stream().map(id -> PREFIX + id).toList();
        List<BoardStats> cached = redisTemplate.opsForValue().multiGet(keys);

        Map<String, BoardStats> result = new HashMap<>();
        List<String> missedIds = new ArrayList<>();

        for (int i = 0; i < qnaIds.size(); i++) {
            BoardStats stat = cached.get(i);
            if (stat != null) {
                result.put(qnaIds.get(i), stat);
            } else {
                missedIds.add(qnaIds.get(i));
            }
        }

        if (!missedIds.isEmpty()) {
            List<BoardStats> fallback = fallbackFn.apply(missedIds);
            for (BoardStats stat : fallback) {
                result.put(stat.getQnaId(), stat);
                redisTemplate.opsForValue().set(PREFIX + stat.getQnaId(), stat, 1);
            }
        }

        return result;
    }
}
