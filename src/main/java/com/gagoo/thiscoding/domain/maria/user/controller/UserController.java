package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity
                .ok(userService.checkEmailDuplicate(email));
    }

    @GetMapping("nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        return ResponseEntity
                .ok(userService.checkNicknameDuplicate(nickname));
    }
}
