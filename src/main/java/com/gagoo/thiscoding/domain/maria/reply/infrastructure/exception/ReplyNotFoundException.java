package com.gagoo.thiscoding.domain.maria.reply.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ReplyNotFoundException extends GlobalException {
    public ReplyNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
