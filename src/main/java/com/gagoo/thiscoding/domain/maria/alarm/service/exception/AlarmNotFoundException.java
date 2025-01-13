package com.gagoo.thiscoding.domain.maria.alarm.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class AlarmNotFoundException extends GlobalException {
    public AlarmNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
