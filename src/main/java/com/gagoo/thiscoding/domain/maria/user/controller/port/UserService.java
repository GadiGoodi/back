package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    User create(UserCreate userCreate);

    User updateNickname(UpdateProfileNicknameRequest request);

    User updateImage(UpdateProfileImageRequest updateProfileImageRequest);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
