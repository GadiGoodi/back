package com.gagoo.thiscoding.domain.maria.usercoderoom.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response.ParticipationsResponse;
import com.gagoo.thiscoding.global.paging.dto.PageResponse;
import com.gagoo.thiscoding.global.paging.dto.PageResponseFactory;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class UserCodeRoomParticipationsController {
    private final UserCodeRoomService userCodeRoomService;

    // 참여 중인 코드방 목록 조회
    @GetMapping
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<PageResponse<ParticipationsResponse>> getParticipationList(@RequestParam(defaultValue = "1") int page) {
        Page<ParticipationsResponse> userCodeRooms = userCodeRoomService.getParticipations(page);
        return ResponseEntity
                .ok()
                .body(PageResponseFactory.create(userCodeRooms));
    }

    // 참여 중인 코드방 입/퇴장
    @PatchMapping("/access")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<?> accessParticipation(@RequestParam Long id) {
        userCodeRoomService.accessUserCodeRoom(id);
        return ResponseEntity
                .ok()
                .body("코드방 입/퇴장 성공");
    }

    // 참여 중인 코드방 탈퇴
    @DeleteMapping("/leave")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<?> leaveParticipation(@RequestParam Long id) {
        userCodeRoomService.leaveUserCodeRoom(id);
        return ResponseEntity
                .ok()
                .body("코드방 탈퇴 성공");
    }
}
