package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageQnaService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.controller.response.MyPageQnA;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;
    private final MyPageQnaService myPageQnaService;

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

    @PatchMapping("/me")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfile updateProfile) {
        userService.updateImage(updateProfile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}/invitations/{userCodeRoomId}/cancel")
    public ResponseEntity<String> cancelInvitation(@PathVariable Long userId, @PathVariable Long userCodeRoomId) {
        myPageService.cancelCodeRoom(userCodeRoomId);
        return ResponseEntity.ok("거절 완료");
    }

    @GetMapping("/{userId}/qna")
    public ResponseEntity<Page<MyPageQnA>> getMyPageQnA(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(myPageQnaService.getMyPagePostQnA(userId, pageable).map(MyPageQnA::from));

    }
}
