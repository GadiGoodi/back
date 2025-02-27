package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import com.gagoo.thiscoding.domain.maria.user.service.port.RefreshTokenStore;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AlreadyCreateEmail;
import com.gagoo.thiscoding.domain.maria.user.service.exception.ExistUserNickname;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final HttpServletUtils httpServletUtils;
    private final RefreshTokenStore refreshTokenStore;
    private final SecurityUtils securityUtils;

    /**
     * 회원가입
     * @param userCreate
     * @return 회원가입한 user 정보
     */
    @Override
    public User create(UserCreate userCreate) {
        passwordService.validatePasswordMatch(userCreate.getPassword(), userCreate.getCheckPassword());
        String encodedPassword = passwordService.encode(userCreate.getPassword());
        User user = User.create(userCreate, encodedPassword);

        return userRepository.save(user);
    }

    /**
     * 회원가입 진행 시 이메일 존재 여부 검증
     * @return 존재하지 않을 경우 false
     */
    @Override
    public boolean checkEmailDuplicate(String email) {
        return validateEmailExists(email);
    }

    /**
     * 회원가입 진행 시 닉네임 존재 여부 검증
     * @return 존재하지 않을 경우 false
     */
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        return validateNicknameExists(nickname);
    }

    /**
     * 닉네임 변경
     */
    @Override
    public User updateNickname(UpdateProfileNicknameRequest request) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        validateNicknameExists(currentUser.getNickname());

        User updateUser = currentUser.updateNickname(request.nickname());

        return userRepository.save(updateUser);
    }

    /**
     * 회원 프로필 이미지 수정
     */
    @Override
    public User updateImage(UpdateProfileImageRequest request) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        User updateUser = currentUser.updateProfile(request.imageUrl());

        return userRepository.save(updateUser);
    }

    /**
     * 로그아웃
     * 헤더, 쿠키, 레디스에 저장된 토큰 삭제
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        httpServletUtils.setHeader(response, AUTHORIZATION, "");
        httpServletUtils.removeCookie(request, response, AUTHORIZATION);

        refreshTokenStore.remove(securityUtils.getUserEmail());
    }

    /**
     * 이메일 중복 검증
     */
    private boolean validateEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyCreateEmail(ErrorCode.ALREADY_CREATE_EMAIL);
        }

        return false;
    }

    /**
     * 닉네임 중복 검증
     */
    private boolean validateNicknameExists(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new ExistUserNickname(ErrorCode.EXIST_MEMBER_NICKNAME);
        }

        return false;
    }
}
