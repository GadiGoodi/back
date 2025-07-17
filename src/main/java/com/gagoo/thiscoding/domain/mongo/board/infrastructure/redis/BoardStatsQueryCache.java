package com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BoardStatsQueryCache {

    private static final String HASH_PREFIX = "qnaStats:";

    private final HashOperations<String, String, String> hashOperations;
    private final BoardStatsCommandCache boardStatsCommandCache;

    /**
     * Redis에서 BoardStats를 조회하고, 없으면 fallback으로 MongoDB에서 조회 후 캐시
     */
    public Map<String, BoardStats> getWithFallback(
            List<String> qnaIds,
            Function<List<String>, List<BoardStats>> mongoFallback
    ) {
        Map<String, BoardStats> resultMap = new HashMap<>();
        List<String> missedIds = new ArrayList<>();

        for (String qnaId : qnaIds) {
            String redisKey = HASH_PREFIX + qnaId;
            Map<String, String> hash = hashOperations.entries(redisKey);

            if (hash.isEmpty()) {
                missedIds.add(qnaId);
            } else {
                resultMap.put(qnaId, mapToBoardStats(qnaId, hash));
            }
        }

        if (!missedIds.isEmpty()) {
            List<BoardStats> fallbackStats = mongoFallback.apply(missedIds);
            for (BoardStats stats : fallbackStats) {
                resultMap.put(stats.getQnaId(), stats);
                boardStatsCommandCache.cacheStats(stats);
            }
        }

        return resultMap;
    }

    /**
     * 단일 QnA ID로 통계 조회
     */
    public Optional<BoardStats> getStats(String qnaId) {
        Map<String, String> hash = hashOperations.entries(HASH_PREFIX + qnaId);
        return hash.isEmpty() ? Optional.empty() : Optional.of(mapToBoardStats(qnaId, hash));
    }

    // 유틸 메서드

    private BoardStats mapToBoardStats(String qnaId, Map<String, String> hash) {
        return BoardStats.builder()
                .qnaId(qnaId)
                .answerCount(Long.parseLong(hash.getOrDefault("answerCount", "0")))
                .replyCount(Long.parseLong(hash.getOrDefault("replyCount", "0")))
                .likeCount(Long.parseLong(hash.getOrDefault("likeCount", "0")))
                .viewCount(Long.parseLong(hash.getOrDefault("viewCount", "0")))
                .build();
    }
}