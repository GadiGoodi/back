package com.gagoo.thiscoding.domain.maria.reply.infrastructure.impl;


import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.ReplyEntity;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.jpa.ReplyJpaRepository;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final ReplyJpaRepository replyJpaRepository;
    private final ReplyCustomRepository replyCustomRepository;

    @Override
    public Reply save(Reply reply) {
        return replyJpaRepository.save(ReplyEntity.from(reply)).toModel();
    }

    @Override
    public Optional<Reply> findById(Long replyId) {
        return replyJpaRepository.findById(replyId).map(ReplyEntity::toModel);
    }

    @Override
    public void delete(Reply reply){
        replyJpaRepository.delete(ReplyEntity.from(reply));
    }

    @Override
    public boolean existsById(Long parentId) {
        return replyJpaRepository.existsById(parentId);
    }

    @Override
    public Page<ReplyList> findByQnaId(String qnaId, Pageable pageable) {
        return replyCustomRepository.findByQnaId(qnaId, pageable);
    }
}
