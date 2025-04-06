package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.InvitationCodeRoomResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-page/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<CustomPageDto<InvitationCodeRoomResponse>> CodeRoomInviteListView(Pageable pageable) {
        Page<InvitationCodeRoomResponse> codeRooms = invitationService.getInvitationCodeRoomList(pageable).map(InvitationCodeRoomResponse::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(codeRooms));
    }

    @PutMapping("/alarms/{alarmId}/codeRooms/{codeRoomId}")
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<?> acceptInvitation(@PathVariable Long codeRoomId, @PathVariable Long alarmId) {
        invitationService.acceptInvitationCodeRoom(codeRoomId,alarmId);
        return ResponseEntity
            .ok()
            .body("수락 완료");
    }

    @DeleteMapping("/alarms/{alarmId}/codeRooms/{codeRoomId}")
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<?> rejectInvitation(@PathVariable Long codeRoomId, @PathVariable Long alarmId) {
        invitationService.rejectInvitationCodeRoom(codeRoomId, alarmId);
        return ResponseEntity
            .ok()
            .body("거절 완료");
    }
}
