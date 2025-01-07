package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port;


import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response.ParticipationsResponse;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import org.springframework.data.domain.Page;

public interface UserCodeRoomService {
    boolean createUserCodeRoom(Long userId, Long roomId);

    Page<InviteCodeRoom> getUserCodeRooms(int page);

    void acceptCodeRoom(Long codeRoomId);

    void cancelCodeRoom(Long codeRoomId);

    Page<ParticipationsResponse> getParticipations(int page);

    void accessUserCodeRoom(Long id);

    void leaveUserCodeRoom(Long id);
}
