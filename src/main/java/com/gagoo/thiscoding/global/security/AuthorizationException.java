package com.gagoo.thiscoding.global.security;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class AuthorizationException extends GlobalException {
    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
