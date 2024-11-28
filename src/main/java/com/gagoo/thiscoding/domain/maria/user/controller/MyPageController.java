package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/{userId}/invitations")
    public ResponseEntity<Page<UserCodeRoom>> CodeRoomInviteListView(@PathVariable Long userId, Pageable pageable) {
        Page<UserCodeRoom> userCodeRooms = myPageService.getUserCodeRooms(userId, pageable);
        return ResponseEntity.ok(userCodeRooms);
    }

    @PutMapping("{userId}/invitations/{userCodeRoomId}/accept")
    public ResponseEntity<String> acceptInvitation(@PathVariable Long userId, @PathVariable Long userCodeRoomId) {
        myPageService.acceptCodeRoom(userCodeRoomId);
        return ResponseEntity.ok("수락 완료");
    }

    @DeleteMapping("{userId}/invitations/{userCodeRoomId}/cancel")
    public ResponseEntity<String> cancelInvitation(@PathVariable Long userId, @PathVariable Long userCodeRoomId) {
        myPageService.cancelCodeRoom(userCodeRoomId);
        return ResponseEntity.ok("거절 완료");
    }
}
