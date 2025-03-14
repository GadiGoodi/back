package com.gagoo.thiscoding.domain.maria.like.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.like.domain.Like;
import com.gagoo.thiscoding.domain.maria.like.infrastructure.LikeEntity;
import com.gagoo.thiscoding.domain.maria.like.infrastructure.jpa.LikeJpaRepository;
import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Like save(Like like) {
        return likeJpaRepository.save(LikeEntity.from(like)).toModel();
    }

    @Override
    public Optional<Like> findByQnaIdAndUserId(String qnaId, Long userId) {
        return likeJpaRepository.findByQnaIdAndUserId(qnaId, userId).map(LikeEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        likeJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByQnaIdAndUserId(String qnaId, Long userId) {
        return likeJpaRepository.existsByQnaIdAndUserId(qnaId, userId);
    }
}
