package com.gagoo.thiscoding.domain.maria.reply.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class NotReplyAuthorException extends GlobalException {
    public NotReplyAuthorException(ErrorCode errorCode) {
        super(errorCode);
    }

}
