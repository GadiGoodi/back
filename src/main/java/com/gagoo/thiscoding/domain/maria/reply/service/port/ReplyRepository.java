package com.gagoo.thiscoding.domain.maria.reply.service.port;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);
    Optional<Reply> findById(Long id);
    void delete(Reply reply);
    boolean existsById(Long parentId);
    Page findByQnaId(String qnaId, Pageable pageable);
}
