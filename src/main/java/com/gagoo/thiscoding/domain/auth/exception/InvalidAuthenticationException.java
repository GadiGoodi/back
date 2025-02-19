package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class InvalidAuthenticationException extends GlobalException {
    public InvalidAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
