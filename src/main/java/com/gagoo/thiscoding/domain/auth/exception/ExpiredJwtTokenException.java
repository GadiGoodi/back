package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExpiredJwtTokenException extends GlobalException {
    public ExpiredJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
