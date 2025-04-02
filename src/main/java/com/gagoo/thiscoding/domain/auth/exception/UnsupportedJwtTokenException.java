package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class UnsupportedJwtTokenException extends GlobalException {
    public UnsupportedJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
