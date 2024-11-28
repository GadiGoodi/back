package com.gagoo.thiscoding.domain.maria.user.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class JoinCodeNotFoundException extends GlobalException {
    public JoinCodeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
