package com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class UserCodeRoomAlreadyExistsException extends GlobalException {
    public UserCodeRoomAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
