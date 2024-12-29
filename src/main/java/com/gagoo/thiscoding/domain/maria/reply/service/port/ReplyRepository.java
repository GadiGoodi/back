package com.gagoo.thiscoding.domain.maria.reply.service.port;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import java.util.Optional;

public interface ReplyRepository {

    Optional<Reply> findById(Long id);
    void delete(Reply reply);
}
