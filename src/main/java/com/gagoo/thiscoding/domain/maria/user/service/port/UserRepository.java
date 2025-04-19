package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Page<User> findByNicknameContaining(String nickname, Long myId, Pageable pageable);
    
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
