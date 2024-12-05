package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.CodeRoomEnterResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/codingrooms")
@RequiredArgsConstructor
public class CodeRoomController {

    private final CodeRoomService codeRoomService;

    // 코드방 입장
    @GetMapping("/{uuid}")
    public ResponseEntity<CodeRoomEnterResponse> enterCodeRoom(@PathVariable String uuid) {
        UUID uuidObject = UUID.fromString(uuid);
        CodeRoom codeRoom = codeRoomService.enterCodeRoom(uuidObject);
        return ResponseEntity
                .ok(CodeRoomEnterResponse.from(codeRoom));
    }
}
