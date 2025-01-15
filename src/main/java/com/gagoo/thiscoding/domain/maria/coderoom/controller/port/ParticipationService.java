package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.ParticipatingCodeRoomResponse;
import org.springframework.data.domain.Page;

public interface ParticipationService {
    Page<ParticipatingCodeRoomResponse> getParticipations(int page);

    void accessUserCodeRoom(Long id);

    void leaveUserCodeRoom(Long id);
}
