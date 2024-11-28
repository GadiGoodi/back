package com.gagoo.thiscoding.domain.maria.user.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExistUserNickname extends GlobalException {
    public ExistUserNickname(ErrorCode errorCode) {
        super(errorCode);
    }
}
