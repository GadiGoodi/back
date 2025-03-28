package com.gagoo.thiscoding.domain.maria.friend.service.Exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class FriendInvalidRequestException extends GlobalException {

    public FriendInvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
