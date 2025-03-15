package com.gagoo.thiscoding.domain.maria.like.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.like.infrastructure.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeJpaRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByQnaIdAndUserId(String qnaId, Long userId);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);
}
