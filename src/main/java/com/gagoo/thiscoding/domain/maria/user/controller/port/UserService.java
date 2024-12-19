package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UserCreate;

public interface UserService {
    User create(UserCreate userCreate);

    User getByEmail(String email);

    User getById(Long id);

    User updateImage(Long id, String imageUrl);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);
}
