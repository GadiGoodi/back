package com.gagoo.thiscoding.domain.mongo.code.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExistCodeFileName extends GlobalException {
    public ExistCodeFileName(ErrorCode errorCode) { super(errorCode); }
}
