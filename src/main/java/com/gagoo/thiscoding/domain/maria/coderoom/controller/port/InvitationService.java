package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.InvitedCodeRoom;
import org.springframework.data.domain.Page;

public interface InvitationService {
    Page<InvitedCodeRoom> findInvitedCodeRoomsByUser(int page);
    void acceptCodeRoom(Long alarmId, Long codeRoomId);
    void cancelCodeRoom(Long alarmId, Long codeRoomId);
}
