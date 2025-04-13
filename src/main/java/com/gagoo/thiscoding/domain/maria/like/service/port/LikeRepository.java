package com.gagoo.thiscoding.domain.maria.like.service.port;

import com.gagoo.thiscoding.domain.maria.like.domain.Like;

import java.util.Optional;
import java.util.List;

public interface LikeRepository {
    Like save(Like like);
    Optional<Like> findByQnaIdAndUserId(String qnaId, Long userId);
    void deleteById(Long id);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);
    List<String> findQnaIdsByQnaIdsAndUserId(List<String> qnaIds, Long userId);
}
