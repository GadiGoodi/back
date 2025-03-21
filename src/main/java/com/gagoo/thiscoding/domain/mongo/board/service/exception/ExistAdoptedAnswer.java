package com.gagoo.thiscoding.domain.mongo.board.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExistAdoptedAnswer extends GlobalException {
    public ExistAdoptedAnswer(ErrorCode errorCode) {
        super(errorCode);
    }
}
