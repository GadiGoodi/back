package com.gagoo.thiscoding.domain.maria.coderoom.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class AlreadyJoinedCodeRoomException extends GlobalException {
    public AlreadyJoinedCodeRoomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
