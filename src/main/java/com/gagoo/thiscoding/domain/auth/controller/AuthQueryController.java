package com.gagoo.thiscoding.domain.auth.controller;

import com.gagoo.thiscoding.domain.auth.controller.port.AuthService;
import com.gagoo.thiscoding.domain.auth.controller.response.UserResponse;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.AUTHORIZATION;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthQueryController {

    private final AuthService authService;
    private final HttpServletUtils servletUtils;

    @GetMapping("/user-info")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ResponseEntity<UserResponse> getOauthUserInfo(HttpServletResponse response) {
        LoginDto loginDto = authService.getUserInfo();
        servletUtils.setHeader(response, AUTHORIZATION, loginDto.getToken().getAtk());
        servletUtils.addCookie(response, AUTHORIZATION, loginDto.getToken().getRtk(), loginDto.getToken().getRtkExpTime());

        return ResponseEntity.ok(UserResponse.from(loginDto.getUser()));
    }
}
