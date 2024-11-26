package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CertificationService certificationService;

    /**
     * 인증코드 발송
     */
    @PostMapping("/join-code")
    public ResponseEntity<Void> sendJoinCode(@RequestParam String email) {
        certificationService.sendJoinCode(email);

        return ResponseEntity.ok().build();
    }

}
