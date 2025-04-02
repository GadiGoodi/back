package com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;
import com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.BookmarkEntity;
import com.gagoo.thiscoding.domain.maria.bookmark.infrastructure.jpa.BookmarkJpaRepository;
import com.gagoo.thiscoding.domain.maria.bookmark.service.port.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepository {

    private final BookmarkJpaRepository bookmarkJpaRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkJpaRepository.save(BookmarkEntity.from(bookmark)).toModel();
    }

    @Override
    public Optional<Bookmark> findByQnaIdAndUserId(String qnaId, Long userId) {
        return bookmarkJpaRepository.findByQnaIdAndUserId(qnaId, userId).map(BookmarkEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        bookmarkJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByQnaIdAndUserId(String qnaId, Long userId) {
        return bookmarkJpaRepository.existsByQnaIdAndUserId(qnaId, userId);
    }

    @Override
    public List<String> findQnaIdsByUserIdPaged(Long userId, Pageable pageable) {
        return bookmarkJpaRepository.findQnaIdsByUserIdPaged(userId, pageable);
    }
}
