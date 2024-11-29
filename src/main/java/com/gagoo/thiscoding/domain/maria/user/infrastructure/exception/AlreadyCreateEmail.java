package com.gagoo.thiscoding.domain.maria.user.infrastructure.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class AlreadyCreateEmail extends GlobalException {
    public AlreadyCreateEmail(ErrorCode errorCode) {
        super(errorCode);
    }
}
