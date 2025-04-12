package com.gagoo.thiscoding.domain.auth.controller;

import com.gagoo.thiscoding.domain.auth.controller.port.AuthService;
import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.response.UserResponse;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final HttpServletUtils servletUtils;

    /**
     * 회원 가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Void> create(@Valid @RequestBody UserCreate userCreate) {
        authService.create(userCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            HttpServletResponse response,
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest);
        setAuthTokens(response, loginDto);

        return ResponseEntity.ok(UserResponse.from(loginDto.getUser()));
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/change-password")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);

        return ResponseEntity.ok().body("비밀번호 변경이 완료되었습니다.");
    }

    /**
     * 비밀번호 초기화
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);

        return ResponseEntity.ok().body("비밀번호 변경이 완료되었습니다.");
    }

    /**
     * 소셜 로그인 유저 정보 조회
     */
    @GetMapping("/oauth/user-info")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ResponseEntity<UserResponse> getOauthUserInfo(HttpServletResponse response) {
        LoginDto loginDto = authService.getUserInfo();
        setAuthTokens(response, loginDto);

        return ResponseEntity.ok(UserResponse.from(loginDto.getUser()));
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.removeToken();

        servletUtils.setHeader(response, AUTHORIZATION, "");
        servletUtils.removeCookie(request, response, AUTHORIZATION);

        return ResponseEntity.ok().build();
    }

    /**
     * 헤더와 쿠키에 토큰 적용
     */
    private void setAuthTokens(HttpServletResponse response, LoginDto loginDto) {
        servletUtils.setHeader(response, AUTHORIZATION, loginDto.getToken().getAtk());
        servletUtils.addCookie(response, AUTHORIZATION, loginDto.getToken().getRtk(), loginDto.getToken().getRtkExpTime());
    }
}
