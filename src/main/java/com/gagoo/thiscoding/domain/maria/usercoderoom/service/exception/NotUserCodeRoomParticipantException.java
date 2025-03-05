package com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class NotUserCodeRoomParticipantException extends GlobalException {
    public NotUserCodeRoomParticipantException(ErrorCode errorCode) {
        super(errorCode);
    }
}
