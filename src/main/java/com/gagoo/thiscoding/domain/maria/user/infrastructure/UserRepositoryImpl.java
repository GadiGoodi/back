package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
}
