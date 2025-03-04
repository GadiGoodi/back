package com.gagoo.thiscoding.domain.maria.coderoom.controller.port;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.InvitationCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import org.springframework.data.domain.Pageable;

public interface InvitationService {
    CustomPageDto<InvitationCodeRoomResponse> getInvitationCodeRoomList(Pageable pageable);
    UserCodeRoom acceptInvitationCodeRoom(Long codeRoomId, Long alarmId);
    void rejectInvitationCodeRoom(Long codeRoomId, Long alarmId);
}
