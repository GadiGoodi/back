package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
