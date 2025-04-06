package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.controller.port.AuthService;
import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.auth.domain.Token;
import com.gagoo.thiscoding.domain.auth.dto.LoginDto;
import com.gagoo.thiscoding.domain.auth.service.port.TokenFactory;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final SecurityUtils securityUtils;
    private final TokenFactory tokenFactory;

    /**
     * 회원가입
     * @param userCreate
     * @return 회원가입한 user 정보
     */
    @Override
    public User create(UserCreate userCreate) {
        preparePassword(userCreate.getPassword(), userCreate.getCheckPassword());
        User user = User.create(userCreate, passwordService.getPasswordEncoder());

        return userRepository.save(user);
    }

    /**
     * 로그인
     */
    @Override
    public LoginDto login(LoginRequest request) {
        User user = userRepository.getByEmail(request.email());

        validateUserActivation(user);
        passwordService.matchPassword(request.password(), user.getPassword());
        Token token = tokenFactory.createToken(user);

        return LoginDto.of(user, token);
    }

    /**
     * 마이페이지 비밀번호 변경
     * - 비밀번호 변경
     */
    @Override
    public User changePassword(ChangePasswordRequest request) {
        User currentUser = getCurrentUser();
        passwordService.matchPassword(request.currentPassword(), currentUser.getPassword());

        return updatePassword(currentUser, request.newPassword(), request.checkPassword());
    }

    /**
     * 비밀번호 초기화
     */
    @Override
    public User resetPassword(ResetPasswordRequest request) {
        User user = userRepository.getByEmail(request.email());
        return updatePassword(user, request.newPassword(), request.checkPassword());
    }

    /**
     * 로그아웃시 리프레시 토큰 삭제
     */
    @Override
    public void removeToken() {
        tokenFactory.remove(getCurUserEmail());
    }

    /**
     * 비밀번호 변경 공통 로직 메서드
     */
    private User updatePassword(User user, String newPassword, String checkPassword) {
        passwordService.validatePasswordMatch(newPassword, checkPassword);
        User updateUser = user.changePassword(newPassword, passwordService.getPasswordEncoder());

        return userRepository.save(updateUser);
    }

    @Override
    public LoginDto getUserInfo() {
        User currentUser = getCurrentUser();
        Token token = tokenFactory.createToken(currentUser);

        return LoginDto.of(currentUser, token);
    }

    /**
     * 현재 로그인한 유저 정보 반환
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(getCurUserEmail());
    }

    /**
     * 현재 로그인한 유저 이메일 반환
     */
    private String getCurUserEmail() {
        return securityUtils.getUserEmail();
    }


    /**
     * 비밀번호 검증 및 암호화
     */
    private void preparePassword(String password, String checkPassword) {
        passwordService.validatePasswordMatch(password, checkPassword);
    }

    /**
     * 탈퇴한 회원인지 확인
     */
    private static void validateUserActivation(User user) {
        if (!user.isActivated()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
