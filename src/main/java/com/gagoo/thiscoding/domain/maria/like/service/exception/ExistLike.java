package com.gagoo.thiscoding.domain.maria.like.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExistLike extends GlobalException {
    public ExistLike(ErrorCode errorCode) {
        super(errorCode);
    }
}
