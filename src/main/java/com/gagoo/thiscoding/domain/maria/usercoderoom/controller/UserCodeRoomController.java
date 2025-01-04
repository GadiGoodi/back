package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import com.gagoo.thiscoding.global.paging.dto.PageResponseFactory;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class UserCodeRoomController {
    private final UserCodeRoomService userCodeRoomService;

    @GetMapping
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<PageResponse<InviteCodeRoom>> CodeRoomInviteListView(
        @RequestParam(defaultValue = "1") int page) {
        Page<InviteCodeRoom> codeRooms = userCodeRoomService.getUserCodeRooms(page);
        return ResponseEntity
            .ok()
            .body(PageResponseFactory.create(codeRooms));
    }

    @PutMapping("/{codeRoomId}/accept")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<?> acceptInvitation(@PathVariable Long codeRoomId) {
        userCodeRoomService.acceptCodeRoom(codeRoomId);
        return ResponseEntity
            .ok()
            .body("수락 완료");
    }

    @DeleteMapping("/{codeRoomId}/cancel")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<?> cancelInvitation(@PathVariable Long codeRoomId) {
        userCodeRoomService.cancelCodeRoom(codeRoomId);
        return ResponseEntity
            .ok()
            .body("거절 완료");
    }

    // 참여 중인 코드방 입/퇴장
    @PatchMapping("/{id}/access")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<?> accessParticipation(@PathVariable Long id) {
        userCodeRoomService.accessUserCodeRoom(id);
        return ResponseEntity
                .ok()
                .body("코드방 입/퇴장 성공");
    }
}
