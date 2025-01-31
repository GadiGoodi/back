package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.CodeRoomEnterResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomEnter;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/codingrooms")
@RequiredArgsConstructor
public class CodeRoomController {

    private final CodeRoomService codeRoomService;

    // 코드방 입장 (조회)
    @GetMapping("/{uuid}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CodeRoomEnterResponse> enterCodeRoom(@PathVariable String uuid) {
        CodeRoomEnter codeRoomEnter = codeRoomService.enterCodeRoom(uuid);

        return ResponseEntity
                .ok(CodeRoomEnterResponse.from(codeRoomEnter));
    }
}
