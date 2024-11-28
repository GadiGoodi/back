package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/join-code")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    /**
     * 인증코드 발송
     */
    @PostMapping
    public ResponseEntity<Void> sendJoinCode(@NotBlank @RequestParam String email) {
        certificationService.sendJoinCode(email);

        return ResponseEntity.ok().build();
    }

    /**
     * 인증코드 확인
     */
    @PostMapping("/check")
    public ResponseEntity<Void> checkJoinCode(@Valid @RequestBody JoinCode joinCode) {
        certificationService.checkJoinCode(joinCode);

        return ResponseEntity.ok().build();
    }
}
