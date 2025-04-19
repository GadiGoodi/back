package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.jpa.UserJpaRepository;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }


    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::toModel);
    }

    /**
     * 이메일로 유저 객체 조회
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
    }

    /**
     * 닉네임으로 유저 객체 조회
     */
    @Override
    public Optional<User> findByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname).map(UserEntity::toModel);
    }

    /**
     * 닉네임으로 유저 객체 리스트 조회
     * */
    @Override
    public Page<User> findByNicknameContaining(String nickname, Long myId, Pageable pageable) {
        return userJpaRepository.findByNicknameContainingAndRoleAndIsActivatedTrueAndIsBannedFalseAndIdNot(nickname,
            Role.USER, myId,pageable).map(UserEntity::toModel);
    }
}
