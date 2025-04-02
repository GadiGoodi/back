package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class MalformedFormJwtTokenException extends GlobalException {
    public MalformedFormJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
