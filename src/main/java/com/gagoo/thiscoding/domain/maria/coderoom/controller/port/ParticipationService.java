package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.ParticipatingCodeRoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipationService {
    Page<ParticipatingCodeRoomResponse> getParticipations(Pageable pageable);

    void accessUserCodeRoom(Long id);

    void leaveUserCodeRoom(Long id);
}
