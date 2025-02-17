package com.gagoo.thiscoding.global.security.controller;

import com.gagoo.thiscoding.global.security.controller.port.AuthService;
import com.gagoo.thiscoding.global.security.controller.request.LoginRequest;
import com.gagoo.thiscoding.global.security.controller.response.UserResponse;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gagoo.thiscoding.global.security.common.AuthConstants.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final HttpServletUtils servletUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletResponse response,
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest);

        servletUtils.setHeader(response, AUTHORIZATION, loginDto.getToken().getBearerAtk());
        servletUtils.addCookie(response, AUTHORIZATION, loginDto.getToken().getRtk(), loginDto.getToken().getRtkExpTime());

        return ResponseEntity.ok(UserResponse.from(loginDto.getUser()));
    }
}
