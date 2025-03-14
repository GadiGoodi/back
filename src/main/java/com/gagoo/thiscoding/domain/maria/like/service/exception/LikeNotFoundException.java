package com.gagoo.thiscoding.domain.maria.like.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class LikeNotFoundException extends GlobalException {
    public LikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
