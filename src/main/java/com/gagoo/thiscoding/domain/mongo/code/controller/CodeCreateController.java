package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.code.controller.port.CodeService;
import com.gagoo.thiscoding.domain.mongo.code.controller.response.CodeResponse;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeCreateController {
    private final CodeService codeService;

    // 코드 파일 생성 (저장)
    @PostMapping("/create")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CodeResponse> createCode(@RequestBody CodeCreate codeCreate) {
        Code code = codeService.createCode(codeCreate);

        return ApiResponse
                .created(CodeResponse.from(code),"코드 파일 생성 (저장) 성공");
    }

    // 코드 파일 저장
    @PostMapping("/save")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CodeResponse> saveCode(@RequestBody CodeCreate codeCreate) {
        Code code = codeService.saveCode(codeCreate);

        return ApiResponse.created(CodeResponse.from(code), "코드 파일 저장 성공");
    }

}
