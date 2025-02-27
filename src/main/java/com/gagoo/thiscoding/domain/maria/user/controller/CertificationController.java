package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.AuthCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> sendJoinCode(@NotBlank @RequestParam String email) {
        certificationService.sendJoinCode(email);

        return ResponseEntity.ok().build();
    }

    /**
     * 임시 비밀번호 발송
     */
    @GetMapping("/temporary-password")
    public ResponseEntity<String> temporaryPassword(@NotBlank @RequestParam String email) {
        certificationService.sendTemporaryPassword(email);

        return ResponseEntity.ok().body("임시 비밀번호 발급");
    }

    /**
     * 인증코드 확인
     */
    @PostMapping("/auth-code/check")
    public ResponseEntity<Void> checkJoinCode(@Valid @RequestBody AuthCode authCode) {
        certificationService.checkAuthCode(authCode);

        return ResponseEntity.ok().build();
    }
}
