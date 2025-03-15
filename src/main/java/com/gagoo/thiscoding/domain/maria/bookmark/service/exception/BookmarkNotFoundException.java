package com.gagoo.thiscoding.domain.maria.bookmark.service.exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class BookmarkNotFoundException extends GlobalException {
    public BookmarkNotFoundException(ErrorCode errorCode) {
      super(errorCode);
    }
}
