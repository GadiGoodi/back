package com.gagoo.thiscoding.domain.maria.reply.service;

import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.domain.Reply;
import com.gagoo.thiscoding.domain.maria.reply.domain.dto.ReplyCreate;
import com.gagoo.thiscoding.domain.maria.reply.infrastructure.exception.ReplyNotFoundException;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.exception.QnaNotFoundException;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 작성
     */
    @Override
    public Reply create(String qnaId, ReplyCreate replyCreate) {
        User currentUser = getByEmail(SecurityUtils.getUserEmail());

        validateCreateReply(qnaId, replyCreate);
        Reply reply = Reply.create(currentUser, qnaId, replyCreate);

        return replyRepository.save(reply);
    }

    @Override
    public CustomPageDto getQnAReply(String qnaId, Pageable pageable) {
        Page<Reply> qnaReply = replyRepository.findByQnaId(qnaId, pageable);

        return new CustomPageDto(qnaReply);
    }

    @Override
    public void delete(Long replyId) {
        Reply reply = getById(replyId);
        replyRepository.delete(reply);
    }

    /**
     * 댓글 작성할 때 필요한 모든 검증 로직
     */
    private void validateCreateReply(String qnaId, ReplyCreate replyCreate) {
        validateQnAId(qnaId);

        if(replyCreate.getParentId() != null) {
            validateReplyId(replyCreate.getParentId());
        }
    }

    /**
     * 부모 댓글이 존재하는지 확인
     */
    private void validateReplyId(Long parentId) {
        if (!replyRepository.existsById(parentId)) {
            throw new ReplyNotFoundException(ErrorCode.REPLY_NOT_FOUND);
        }
    }

    /**
     * 댓글이 존재하는지 확인
     */
    public Reply getById(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(() -> new ReplyNotFoundException(
            ErrorCode.REPLY_NOT_FOUND));
    }

    /**
     * 게시물이 있는지 확인
     */
    private void validateQnAId(String qnaId) {
        if (!boardRepository.existsById(qnaId)) {
            throw new QnaNotFoundException(ErrorCode.QNA_NOT_FOUND);
        }
    }

    private User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
