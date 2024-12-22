package com.gagoo.thiscoding.domain.mongo.code.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class CodeNotFoundException extends GlobalException {
    public CodeNotFoundException(ErrorCode errorCode) { super(errorCode); }
}
