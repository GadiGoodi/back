package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.controller.port.AuthService;
import com.gagoo.thiscoding.domain.auth.controller.request.ChangePasswordRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.LoginRequest;
import com.gagoo.thiscoding.domain.auth.controller.request.ResetPasswordRequest;
import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
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
    private final TokenFactory tokenFactory;
    private final UserFinder userFinder;
    private final SecurityUtils securityUtils;

    /**
     * 회원가입
     * @param userCreate
     * @return 회원가입한 user 정보
     */
    @Override
    public User create(UserCreate userCreate) {
        passwordService.validatePasswordMatch(userCreate.getPassword(), userCreate.getCheckPassword());
        User user = User.create(userCreate, passwordService.getPasswordEncoder());

        return userRepository.save(user);
    }

    /**
     * 로그인
     */
    @Override
    public LoginDto login(LoginRequest request) {
        User user = findUserByEmail(request.email());

        validateUserActivation(user);
        passwordService.matchPassword(request.password(), user.getPassword());

        return createLoginDto(user);
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
        User user = findUserByEmail(request.email());
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
     * 회원 탈퇴
     * - 탈퇴시 isActivated 상태값 false로 변경
     */
    @Override
    public User withdraw() {
        User currentUser = getCurrentUser();
        User withdrawUser = currentUser.withdraw();

        return userRepository.save(withdrawUser);
    }

    @Override
    public LoginDto getUserInfo() {
        User currentUser = getCurrentUser();
        return createLoginDto(currentUser);
    }

    /**
     * 이메일로 사용자 찾기
     * @param email 이메일
     * @return 사용자 정보
     */
    private User findUserByEmail(String email) {
        return userFinder.getByEmail(email);
    }

    /**
     * 비밀번호 변경 공통 로직 메서드
     */
    private User updatePassword(User user, String newPassword, String checkPassword) {
        passwordService.validatePasswordMatch(newPassword, checkPassword);
        User updateUser = user.changePassword(newPassword, passwordService.getPasswordEncoder());

        return userRepository.save(updateUser);
    }

    /**
     * 현재 로그인한 유저 정보 반환
     */
    private User getCurrentUser() {
        return findUserByEmail(getCurUserEmail());
    }

    /**
     * 현재 로그인한 유저 이메일 반환
     */
    private String getCurUserEmail() {
        return securityUtils.getUserEmail();
    }

    /**
     * 탈퇴한 회원인지 확인
     */
    private static void validateUserActivation(User user) {
        if (!user.isActivated()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * 로그인 DTO 생성 공통 메서드
     */
    private LoginDto createLoginDto(User user) {
        Token token = tokenFactory.createToken(user);
        return LoginDto.of(user, token);
    }
}