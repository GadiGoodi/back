package com.gagoo.thiscoding.domain.maria.user.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class PasswordNotEqualException extends GlobalException {
    public PasswordNotEqualException(ErrorCode errorCode) {
        super(errorCode);
    }
}
