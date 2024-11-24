package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CertificationService certificationService;

    /**
     * 인증코드 발송
     */
    @PostMapping("/join-code")
    public ResponseEntity<Void> sendJoinCode(@RequestParam String email) {
        certificationService.sendJoinCode(email);

        return ResponseEntity.ok().build();
    }

    /**
     * 인증코드 확인
     */
    @PostMapping
    public ResponseEntity<Void> checkJoinCode(@RequestParam String joinCode) {
        certificationService.checkJoinCode(joinCode);

        return ResponseEntity.ok().build();
    }

}
