package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.mongo.code.controller.port.CodeService;
import com.gagoo.thiscoding.domain.mongo.code.controller.response.CodeResponse;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeCreateController {
    private final CodeService codeService;

    // 코드 파일 생성
    @PostMapping
    public ResponseEntity<CodeResponse> createCode(@RequestBody CodeCreate codeCreate) {
        Code code = codeService.createCode(codeCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CodeResponse.from(code));
    }
}
