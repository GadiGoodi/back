package com.gagoo.thiscoding.domain.maria.like.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.like.infrastructure.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeJpaRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByQnaIdAndUserId(String qnaId, Long userId);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);

    @Query("SELECT l.qnaId FROM LikeEntity l WHERE l.qnaId IN :qnaId AND l.user.id = :userId")
    List<String> findQnaIdsByQnaIdAndUserId(List<String> qnaId, Long userId);
}
