package com.gagoo.thiscoding.domain.auth.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class SocialEmailNotProvidedException extends GlobalException {
    public SocialEmailNotProvidedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
