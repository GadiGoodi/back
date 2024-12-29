package com.gagoo.thiscoding.domain.maria.reply.service;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.exception.ReplyNotFoundException;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    public Reply getById(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(() -> new ReplyNotFoundException(
            ErrorCode.REPLY_NOT_FOUND));
    }

    @Override
    public void delete(Long replyId) {
        Reply reply = getById(replyId);
        replyRepository.delete(reply);
    }
}
