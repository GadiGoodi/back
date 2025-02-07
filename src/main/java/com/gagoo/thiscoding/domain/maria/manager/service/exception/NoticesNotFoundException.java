package com.gagoo.thiscoding.domain.maria.manager.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class NoticesNotFoundException extends GlobalException {
    public NoticesNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
