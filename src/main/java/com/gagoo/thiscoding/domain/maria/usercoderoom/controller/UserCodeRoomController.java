package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            .body(PageResponse.create(codeRooms));
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
}
