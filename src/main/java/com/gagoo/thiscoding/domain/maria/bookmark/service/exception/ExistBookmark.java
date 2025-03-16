package com.gagoo.thiscoding.domain.maria.bookmark.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class ExistBookmark extends GlobalException {
    public ExistBookmark(ErrorCode errorCode) {
        super(errorCode);
    }
}
