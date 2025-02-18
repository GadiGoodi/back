package com.gagoo.thiscoding.domain.maria.reply.service.port;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReplyRepository {
    Reply save(Reply reply);
    Optional<Reply> findById(Long id);
    Page<ReplyList> findByQnaId(String qnaId, Pageable pageable);
    Long countByQnaId(String qnaId);
    boolean existsById(Long parentId);
    void delete(Reply reply);
    Page<ReplyList> findRepliesByParentId(String qnaId, Long parentId, Pageable pageable);
}
