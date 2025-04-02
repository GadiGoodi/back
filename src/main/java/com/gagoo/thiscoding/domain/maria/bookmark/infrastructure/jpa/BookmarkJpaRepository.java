package com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.BookmarkEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkJpaRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByQnaIdAndUserId(String qnaId, Long userId);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);

    @Query("SELECT b.qnaId FROM BookmarkEntity b WHERE b.user.id = :userId")
    List<String> findQnaIdsByUserIdPaged(Long userId, Pageable pageable);
}
