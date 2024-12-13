package com.gagoo.thiscoding.domain.maria.friend.service.Exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class FriendAlreadyExistsException extends GlobalException {

    public FriendAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
