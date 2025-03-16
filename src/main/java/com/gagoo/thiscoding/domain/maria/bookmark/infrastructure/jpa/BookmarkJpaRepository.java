package com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkJpaRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByQnaIdAndUserId(String qnaId, Long userId);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);
}
