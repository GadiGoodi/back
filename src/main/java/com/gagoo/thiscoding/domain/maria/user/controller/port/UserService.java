package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.dto.UpdateProfileImageRequest;

public interface UserService {
    User updateNickname(UpdateProfileNicknameRequest request);

    User updateImage(UpdateProfileImageRequest updateProfileImageRequest);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);
}
