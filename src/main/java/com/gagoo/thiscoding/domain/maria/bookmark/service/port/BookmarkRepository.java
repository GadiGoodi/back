package com.gagoo.thiscoding.domain.maria.bookmark.service.port;

import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository {
    Bookmark save(Bookmark bookmark);
    Optional<Bookmark> findByQnaIdAndUserId(String qnaId, Long userId);
    void deleteById(Long id);
    boolean existsByQnaIdAndUserId(String qnaId, Long userId);
    List<String> findQnaIdsByUserIdPaged(Long userId, Pageable pageable);
}
