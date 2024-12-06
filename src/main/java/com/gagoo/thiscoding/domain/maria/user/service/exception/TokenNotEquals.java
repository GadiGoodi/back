package com.gagoo.thiscoding.domain.maria.user.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class TokenNotEquals extends GlobalException {
    public TokenNotEquals(ErrorCode errorCode) {
        super(errorCode);
    }
}
