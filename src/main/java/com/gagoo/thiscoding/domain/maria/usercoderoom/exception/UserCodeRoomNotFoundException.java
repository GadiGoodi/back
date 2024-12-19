package com.gagoo.thiscoding.domain.maria.usercoderoom.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class UserCodeRoomNotFoundException extends GlobalException {
    public UserCodeRoomNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
