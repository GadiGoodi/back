package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.InvitationCodeRoomResponse;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
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
    public ApiResponse<CustomPageDto<InvitationCodeRoomResponse>> CodeRoomInviteListView(Pageable pageable) {
        Page<InvitationCodeRoomResponse> codeRooms = invitationService.getInvitationCodeRoomList(pageable).map(InvitationCodeRoomResponse::from);
        return ApiResponse
                .ok(CustomPageDto.of(codeRooms), "");
    }

    @PutMapping("/alarms/{alarmId}/codeRooms/{codeRoomId}")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<?> acceptInvitation(@PathVariable Long codeRoomId, @PathVariable Long alarmId) {
        invitationService.acceptInvitationCodeRoom(codeRoomId,alarmId);
        return ApiResponse
            .ok(null,"수락 완료");
    }

    @DeleteMapping("/alarms/{alarmId}/codeRooms/{codeRoomId}")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<?> rejectInvitation(@PathVariable Long codeRoomId, @PathVariable Long alarmId) {
        invitationService.rejectInvitationCodeRoom(codeRoomId, alarmId);
        return ApiResponse.ok(null, "거절 완료");
    }
}
