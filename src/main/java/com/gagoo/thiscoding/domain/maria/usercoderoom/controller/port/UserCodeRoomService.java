package com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port;


import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response.ParticipationsResponse;
import org.springframework.data.domain.Page;

public interface UserCodeRoomService {
    boolean createUserCodeRoom(Long userId, Long roomId);

    Page<ParticipationsResponse> getParticipations(int page);

    void accessUserCodeRoom(Long id);

    void leaveUserCodeRoom(Long id);
}
