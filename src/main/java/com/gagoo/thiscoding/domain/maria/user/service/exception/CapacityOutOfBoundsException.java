package com.gagoo.thiscoding.domain.maria.user.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class CapacityOutOfBoundsException extends GlobalException {
    public CapacityOutOfBoundsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
