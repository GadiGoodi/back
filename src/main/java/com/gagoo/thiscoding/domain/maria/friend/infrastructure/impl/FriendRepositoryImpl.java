package com.gagoo.thiscoding.domain.maria.friend.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.domain.dto.FriendRequest;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.FriendEntity;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa.FriendJpaRepository;
import com.gagoo.thiscoding.domain.maria.friend.service.port.FriendRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {

    private final FriendJpaRepository friendJpaRepository;

    @Override
    public Optional<Friend> findById(Long userId) {
        return friendJpaRepository.findById(userId).map(FriendEntity::toModel);
    }

    @Override
    public Friend save(Friend friend) {
        return friendJpaRepository.save(FriendEntity.from(friend)).toModel();
    }

    @Override
    public void delete(FriendRequest friendRequest) {
        friendJpaRepository.deleteByReceiverIdAndSenderId(friendRequest.getReceiverId(),
            friendRequest.getSenderId());
    }

    @Override
    public List<Friend> findAllById(Long userId) {
        return friendJpaRepository.findAllByUserId(userId)
            .stream()
            .map(FriendEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByReceiverIdAndSenderId(Long receiverId, Long senderId) {
        return friendJpaRepository.existsByReceiverIdAndSenderId(receiverId, senderId);
    }
}
