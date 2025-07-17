package com.gagoo.thiscoding.domain.mongo.board.infrastructure.redis;

import com.gagoo.thiscoding.domain.mongo.board.domain.BoardStats;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BoardStatsCommandCache {

    private static final String HASH_PREFIX = "qnaStats:";
    private static final Duration CACHE_TTL = Duration.ofHours(1);

    private final Set<String> modifiedKeys = ConcurrentHashMap.newKeySet();
    private final HashOperations<String, String, String> hashOperations;

    /**
     * 조회수 증가
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void incrementViewCount(String qnaId,  Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "viewCount", 1, mongoFallback);
    }

    /**
     * 좋아요수 증가
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void incrementLikeCount(String qnaId, Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "likeCount", 1, mongoFallback);
    }

    /**
     * 답변수 증가
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void incrementAnswerCount(String qnaId, Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "answerCount", 1, mongoFallback);
    }

    /**
     * 댓글수 증가
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void incrementReplyCount(String qnaId, Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "replyCount", 1, mongoFallback);
    }

    /**
     * 좋아요수 감소
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void decrementLikeCount(String qnaId, Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "likeCount", -1, mongoFallback);
    }

    /**
     * 댓글수 감소
     * fallBack 전략으로 캐시에 없을 경우 mongoDB 조회
     */
    public void decrementReplyCount(String qnaId, Function<String, Optional<BoardStats>> mongoFallback) {
        updateStats(qnaId, "replyCount", -1, mongoFallback);
    }

    /**
     * 통계 캐시 저장
     */
    public void cacheStats(BoardStats stats) {
        String key = HASH_PREFIX + stats.getQnaId();
        hashOperations.putAll(key, mapToHash(stats));
        hashOperations.getOperations().expire(key, CACHE_TTL);
    }

    /**
     * 몽고디비에 동기화
     */
    public void flushCache(Function<BoardStats, BoardStats> mongoSaveFunction) {
        if (modifiedKeys.isEmpty()) {
            return;
        }

        Set<String> flushKey = new HashSet<>(modifiedKeys);
        modifiedKeys.clear();

        for (String currentQnaId : flushKey) {
            try {
                String redisKey = HASH_PREFIX + currentQnaId;
                Map<String, String> cachedStats = hashOperations.entries(redisKey);
                if (cachedStats == null || cachedStats.isEmpty()) {
                    continue;
                }

                BoardStats boardStats = convertHashToBoardStats(currentQnaId, cachedStats);
                mongoSaveFunction.apply(boardStats);
            } catch (Exception e) {
                modifiedKeys.add(currentQnaId);
            }
        }
    }

    // 중복 코드 공통 메서드

    private void updateStats(String qnaId, String field, int delta, Function<String, Optional<BoardStats>> mongoFallback) {
        String key = HASH_PREFIX + qnaId;

        checkCache(qnaId, key, mongoFallback);

        modifiedKeys.add(qnaId);
        hashOperations.increment(key, field, delta);
        hashOperations.getOperations().expire(key, CACHE_TTL);
    }

    private void checkCache(String qnaId, String key, Function<String, Optional<BoardStats>> mongoFallback) {
        if (Boolean.FALSE.equals(hashOperations.getOperations().hasKey(key))) {
            BoardStats boardStats = mongoFallback.apply(qnaId)
                    .orElseThrow(() -> new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND));
            cacheStats(boardStats);
        }
    }

    // 유틸 메서드

    private Map<String, String> mapToHash(BoardStats boardStats) {
        String answerCount = Optional.ofNullable(boardStats.getAnswerCount()).orElse(0L).toString();
        String replyCount = Optional.ofNullable(boardStats.getReplyCount()).orElse(0L).toString();
        String likeCount = Optional.ofNullable(boardStats.getLikeCount()).orElse(0L).toString();
        String viewCount = Optional.ofNullable(boardStats.getViewCount()).orElse(0L).toString();

        return Map.of(
                "answerCount", answerCount,
                "replyCount", replyCount,
                "likeCount", likeCount,
                "viewCount", viewCount
        );
    }

    private BoardStats convertHashToBoardStats(String qnaId, Map<String, String> cachedStats) {
        return BoardStats.builder()
                .qnaId(qnaId)
                .viewCount(Optional.ofNullable(cachedStats.get("viewCount")).map(Long::valueOf).orElse(0L))
                .likeCount(Optional.ofNullable(cachedStats.get("likeCount")).map(Long::valueOf).orElse(0L))
                .answerCount(Optional.ofNullable(cachedStats.get("answerCount")).map(Long::valueOf).orElse(0L))
                .replyCount(Optional.ofNullable(cachedStats.get("replyCount")).map(Long::valueOf).orElse(0L))
                .build();
    }
}