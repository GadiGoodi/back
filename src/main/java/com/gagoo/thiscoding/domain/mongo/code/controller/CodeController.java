package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.code.controller.port.CodeService;
import com.gagoo.thiscoding.domain.mongo.code.controller.response.CodeResponse;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    // 코드 파일 조회
    @GetMapping("/{codeId}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CodeResponse> getCode(@PathVariable String codeId) {
        Code code = codeService.getById(codeId);

        return ResponseEntity
                .ok()
                .body(CodeResponse.from(code));
    }

    // 코드 파일 저장
    @PostMapping("/save")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CodeResponse> saveCode(@RequestBody CodeCreate codeCreate) {
        Code code = codeService.saveCode(codeCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CodeResponse.from(code));
    }

}
