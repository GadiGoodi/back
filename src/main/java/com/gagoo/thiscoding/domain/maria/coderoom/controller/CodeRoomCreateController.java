package com.gagoo.thiscoding.domain.maria.coderoom.controller;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.CodeRoomCreateResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.dto.CodeRoomCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/codingrooms-modal")
@RequiredArgsConstructor
public class CodeRoomCreateController {

    private final CodeRoomService codeRoomService;

    // 코드방 생성
    @PostMapping
    public ResponseEntity<CodeRoomCreateResponse> createCodeRoom(@RequestBody CodeRoomCreate codeRoomCreate) {
        CodeRoom codeRoom = codeRoomService.createCodeRoom(codeRoomCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CodeRoomCreateResponse.from(codeRoom));
    }
}
