package com.gagoo.thiscoding.domain.maria.user.service;

import static com.gagoo.thiscoding.global.paging.PageSize.FRIEND;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AlreadyCreateEmail;
import com.gagoo.thiscoding.domain.maria.user.service.exception.ExistUserNickname;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    /**
     * 닉네임으로 회원 객체 리스트 조회
     * */
    @Override
    public Page<User> searchByNickname(String nickname, Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), FRIEND);
        return userRepository.findByNicknameContaining(nickname, customPageable);
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
        User currentUser = userRepository.getByEmail(getCurUserEmail());
        validateNicknameExists(request.nickname());

        User updateUser = currentUser.updateNickname(request.nickname());

        return userRepository.save(updateUser);
    }

    /**
     * 회원 프로필 이미지 수정
     */
    @Override
    public User updateImage(UpdateProfileImageRequest request) {
        User currentUser = userRepository.getByEmail(getCurUserEmail());
        User updateUser = currentUser.updateProfile(request.imageUrl());

        return userRepository.save(updateUser);
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

    /**
     * 현재 로그인한 유저 이메일 반환
     */
    private String getCurUserEmail() {
        return securityUtils.getUserEmail();
    }
}
