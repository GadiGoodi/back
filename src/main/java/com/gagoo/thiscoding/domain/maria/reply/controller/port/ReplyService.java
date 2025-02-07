package com.gagoo.thiscoding.domain.maria.reply.controller.port;

import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.service.dto.ReplyList;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.data.domain.Pageable;

public interface ReplyService {

    void delete(String qnaId, Long replyId);

    Reply create(String qnaId, ReplyCreate replyCreate);

    CustomPageDto<ReplyList> getQnAReply(String qnaId, Pageable pageable);

    CustomPageDto<ReplyList> getReplies(String qnaId,Long parentId, Pageable pageable);
}
