package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User getByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByUserId(Long userId);

}
