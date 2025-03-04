package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitationCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvitationService {
    Page<InvitationCodeRoom> getInvitationCodeRoomList(Pageable pageable);
    UserCodeRoom acceptInvitationCodeRoom(Long codeRoomId, Long alarmId);
    void rejectInvitationCodeRoom(Long codeRoomId, Long alarmId);
}
