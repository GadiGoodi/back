package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port;


import com.gagoo.thiscoding.domain.maria.user.controller.response.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.UserCodeRoomResponse;
import com.gagoo.thiscoding.global.pageDto.CustomPageDto;
import org.springframework.data.domain.Pageable;

public interface UserCodeRoomService {
    UserCodeRoom createUserCodeRoom(Long userId, Long roomId);

    CustomPageDto<InviteCodeRoom> getUserCodeRooms(Long id, Pageable pageable);

    UserCodeRoomResponse acceptCodeRoom(Long codeRoomId, Long userId);

    UserCodeRoomResponse cancelCodeRoom(Long codeRoomId, Long userId);
}
