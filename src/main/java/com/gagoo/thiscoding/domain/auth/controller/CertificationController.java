package com.gagoo.thiscoding.domain.auth.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.AuthCodeRequest;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    /**
     * 회원가입 인증코드 발송
     */
    @GetMapping("/join-code")
    public ApiResponse<Void> sendJoinCode(@NotBlank @RequestParam String email) {
        certificationService.sendJoinCode(email);

        return ApiResponse.ok(null, "인증 코드 발송 성공");
    }

    /**
     * 임시 비밀번호 발송
     */
    @GetMapping("/temporary-password")
    public ApiResponse<String> temporaryPassword(@NotBlank @RequestParam String email) {
        certificationService.sendTemporaryPassword(email);

        return ApiResponse.ok(null,"임시 비밀번호 발급 성공");
    }

    /**
     * 인증코드 확인
     */
    @PostMapping("/auth-code/check")
    public ApiResponse<Void> checkJoinCode(@Valid @RequestBody AuthCodeRequest authCodeRequest) {
        certificationService.checkAuthCode(authCodeRequest);

        return ApiResponse.ok(null, "인증코드 확인 성공");
    }
}
