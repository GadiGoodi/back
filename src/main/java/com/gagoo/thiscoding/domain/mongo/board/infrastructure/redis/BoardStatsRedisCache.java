package com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BoardStatsRedisCache {

    private static final String HASH_PREFIX = "qnaStats:";
    private static final Duration CACHE_TTL = Duration.ofHours(1);
    private final HashOperations<String, String, String> hashOperations;

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
                cacheStats(stats);
                resultMap.put(stats.getQnaId(), stats);
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

    /**
     * 통계 캐시 저장
     * TTL 적용
     */
    public void cacheStats(BoardStats stats) {
        String key = HASH_PREFIX + stats.getQnaId();
        hashOperations.putAll(key, mapToHash(stats));
        hashOperations.getOperations().expire(key, CACHE_TTL);
    }
    /**
     * 캐시 삭제
     */
    public void evictStats(String qnaId) {
        hashOperations.getOperations().delete(HASH_PREFIX + qnaId);
    }

    // 변환 유틸

    private Map<String, String> mapToHash(BoardStats boardStats) {
        return Map.of(
                "answerCount", String.valueOf(boardStats.getAnswerCount()),
                "replyCount", String.valueOf(boardStats.getReplyCount()),
                "likeCount", String.valueOf(boardStats.getLikeCount()),
                "viewCount", String.valueOf(boardStats.getViewCount())
        );
    }

    private BoardStats mapToBoardStats(String qnaId, Map<String, String> hash) {
        return BoardStats.builder()
                .qnaId(qnaId)
                .answerCount(Long.parseLong(hash.get("answerCount")))
                .replyCount(Long.parseLong(hash.get("replyCount")))
                .likeCount(Long.parseLong(hash.get("likeCount")))
                .viewCount(Long.parseLong(hash.get("viewCount")))
                .build();
    }

}
