package com.gagoo.thiscoding.domain.maria.coderoom.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class LikeCountUnderFlowException extends GlobalException {
    public LikeCountUnderFlowException(ErrorCode errorCode) {
      super(errorCode);
    }
}
