package com.gagoo.thiscoding.domain.maria.friend.service.Exception;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;

public class AlreadyFriendRequestException extends GlobalException {

    public AlreadyFriendRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
