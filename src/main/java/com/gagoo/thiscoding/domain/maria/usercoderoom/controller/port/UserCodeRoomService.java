package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port;


import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;

public interface UserCodeRoomService {
    UserCodeRoom createUserCodeRoom(Long userId, Long roomId);
}
