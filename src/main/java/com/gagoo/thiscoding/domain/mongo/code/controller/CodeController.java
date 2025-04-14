package com.gagoo.thiscoding.domain.mongo.code.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.code.controller.port.CodeService;
import com.gagoo.thiscoding.domain.mongo.code.controller.response.CodeListResponse;
import com.gagoo.thiscoding.domain.mongo.code.controller.response.CodeResponse;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeList;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
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
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    // 코드 파일 조회
    @GetMapping("/{codeId}")
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CodeResponse> getCode(@PathVariable String codeId) {
        Code code = codeService.getById(codeId);

        return ApiResponse
                .ok(CodeResponse.from(code), "코드 파일 조회 성공");
    }

    // 코드 파일 목록 조회
    @GetMapping("/{roomId}/file")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER)
    public ApiResponse<CustomPageDto<CodeListResponse>> getCodeList(@PathVariable Long roomId, Pageable pageable) {
        Page<CodeList> codeList = codeService.getCodeList(roomId, pageable);
        System.out.println(codeList);

        return ApiResponse.ok(CustomPageDto.of(codeList.map(CodeListResponse::from)), "코드 파일 목록 조회 완료");
    }
}
