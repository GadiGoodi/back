package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.UserService;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.exception.AlreadyCreateEmail;
import com.gagoo.thiscoding.domain.maria.user.service.exception.ExistUserNickname;
import com.gagoo.thiscoding.domain.maria.user.service.exception.PasswordNotEqualException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param userCreate
     * @return 회원가입한 user 정보
     */
    @Override
    public User create(UserCreate userCreate) {
        validatePasswordsMatch(userCreate);
        User user = User.create(userCreate, passwordEncoder);

        return userRepository.save(user);
    }

    /**
     * 이메일 존재 여부 검증
     * @return 존재하지 않을 경우 false
     */
    @Override
    public boolean checkEmailDuplicate(String email) {
        return validateEmailExists(email);
    }

    /**
     * 닉네임 존재 여부 검증
     * @return 존재하지 않을 경우 false
     */
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        return validateNicknameExists(nickname);
    }

    /**
     * 존재하는 회원인지 확인
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
                );
    }

    /**
     * 회원 프로필 이미지 수정
     */
    @Override
    public User updateImage(UpdateProfile updateProfile) {
        User currentUser = getByEmail(SecurityUtils.getUserEmail());
        User updateUser = currentUser.updateProfile(updateProfile.getImageUrl());

        return userRepository.save(updateUser);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
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
     * 비밀번호와 확인 비밀번호 일치하는지 검증
     */
    private static void validatePasswordsMatch(UserCreate userCreate) {
        if (!userCreate.getPassword().equals(userCreate.getCheckPassword())) {
            throw new PasswordNotEqualException(ErrorCode.PASSWORD_NOT_EQUAL);
        }
    }
}
