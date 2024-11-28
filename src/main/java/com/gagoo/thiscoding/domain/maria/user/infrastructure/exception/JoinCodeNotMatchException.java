package com.gagoo.thiscoding.domain.maria.user.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class JoinCodeNotMatchException extends GlobalException {
    public JoinCodeNotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}