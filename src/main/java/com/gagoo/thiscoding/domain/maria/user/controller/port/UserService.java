package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;

public interface UserService {
    User create(UserCreate userCreate);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);

    void validateUserExistence(Long userId);

    User getById(Long userId);
}
