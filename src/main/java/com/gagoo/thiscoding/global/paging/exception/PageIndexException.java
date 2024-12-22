package com.gagoo.thiscoding.global.paging.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class PageIndexException extends GlobalException {
    public PageIndexException(ErrorCode errorCode) {
        super(errorCode);
    }

}
