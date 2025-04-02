package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ClaimNotFoundException extends GlobalException {
    public ClaimNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
