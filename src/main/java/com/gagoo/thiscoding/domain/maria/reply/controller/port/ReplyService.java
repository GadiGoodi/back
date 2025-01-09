package com.gagoo.thiscoding.domain.maria.reply.controller.port;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService {

    void delete(Long replyId);

    Reply create(String qnaId, ReplyCreate replyCreate);

    CustomPageDto<Page<ReplyList>> getQnAReply(String qnaId, Pageable pageable);
}
