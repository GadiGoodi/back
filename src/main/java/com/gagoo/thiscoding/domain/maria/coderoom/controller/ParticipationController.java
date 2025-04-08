package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.ParticipationService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.ParticipatingCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-page/participations")
@RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService participationService;

    // 참여 중인 코드방 목록 조회
    @GetMapping
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<CustomPageDto<ParticipatingCodeRoomResponse>> getParticipationList(Pageable pageable) {
        Page<ParticipatingCodeRoomResponse> userCodeRooms = participationService.getParticipations(pageable);
        return ResponseEntity
                .ok()
                .body(CustomPageDto.of(userCodeRooms));
    }

    // 참여 중인 코드방 입/퇴장
    @PatchMapping("/{id}/access")
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<Boolean> accessParticipation(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(participationService.accessUserCodeRoom(id));
    }

    // 참여 중인 코드방 탈퇴
    @DeleteMapping("/{id}/leave")
    @AuthorizationRequired(value = Role.USER)
    public ResponseEntity<Boolean> leaveParticipation(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(participationService.leaveUserCodeRoom(id));
    }
}
