package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;


import com.gagoo.thiscoding.domain.maria.user.controller.response.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.UserCodeRoomResponse;
import com.gagoo.thiscoding.global.pageDto.CustomPageDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserCodeRoomController {
    private final UserCodeRoomService userCodeRoomService;

    @GetMapping("/{userId}/invitations")
    public ResponseEntity<CustomPageDto<InviteCodeRoom>> CodeRoomInviteListView(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
            .ok()
            .body(userCodeRoomService.getUserCodeRooms(userId, pageable));
    }

    @PutMapping("/{userId}/invitations/{codeRoomId}/accept")
    public ResponseEntity<UserCodeRoomResponse> acceptInvitation(@PathVariable Long codeRoomId, @PathVariable Long userId) {
        return ResponseEntity
            .ok()
            .body(userCodeRoomService.acceptCodeRoom(codeRoomId, userId));
    }

    @DeleteMapping("/{userId}/invitations/{codeRoomId}/cancel")
    public ResponseEntity<UserCodeRoomResponse> cancelInvitation(@PathVariable Long codeRoomId, @PathVariable Long userId) {
        return ResponseEntity
            .ok()
            .body(userCodeRoomService.cancelCodeRoom(codeRoomId, userId));
    }
}
