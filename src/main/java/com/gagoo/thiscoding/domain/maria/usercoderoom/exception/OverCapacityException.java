package com.gagoo.thiscoding.domain.maria.usercoderoom.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class OverCapacityException extends GlobalException {
    public OverCapacityException(ErrorCode errorCode) {
        super(errorCode);
    }

}
