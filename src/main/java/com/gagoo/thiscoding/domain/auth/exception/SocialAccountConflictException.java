package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class SocialAccountConflictException extends GlobalException {
    public SocialAccountConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
