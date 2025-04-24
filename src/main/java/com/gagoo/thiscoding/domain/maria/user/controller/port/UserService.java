package com.gagoo.thiscoding.domain.maria.user.controller.port;

import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileNicknameRequest;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.controller.request.UpdateProfileImageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> searchByNickname(String nickname, Pageable pageable);

    User updateNickname(UpdateProfileNicknameRequest request);

    User updateImage(UpdateProfileImageRequest updateProfileImageRequest);

    boolean checkEmailDuplicate(String email);

    boolean checkNicknameDuplicate(String nickname);

}
