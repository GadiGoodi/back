package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfile;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    User create(UserCreate userCreate);

    User updateImage(UpdateProfile updateProfile);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
