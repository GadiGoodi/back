package com.gagoo.thiscoding.domain.maria.friend.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.FriendEntity;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa.FriendCustomRepository;
import com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa.FriendJpaRepository;
import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import com.gagoo.thiscoding.domain.maria.friend.service.port.FriendRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {

    private final FriendJpaRepository friendJpaRepository;
    private final FriendCustomRepository friendCustomRepository;

    @Override
    public Optional<Friend> findById(Long friendId) {
        return friendJpaRepository.findById(friendId).map(FriendEntity::toModel);
    }

    @Override
    public Page<FriendInfo> searchFriends(String currentUser, String keyword, Pageable pageable){
        return friendCustomRepository.searchMyFriends(currentUser, keyword, pageable);
    }

    @Override
    public Page<FriendInfo> findMyFriends(String nickname, Pageable pageable){
        return friendCustomRepository.findMyFriends(nickname, pageable);
    }

    @Override
    public Page<FriendInfo> findReceivedFriendRequests(String nickname, Pageable pageable) {
        return friendCustomRepository.findReceivedFriendRequests(nickname, pageable);
    }

    @Override
    public Page<FriendInfo> findSentFriendRequests(String nickname, Pageable pageable){
        return friendCustomRepository.findSentFriendRequests(nickname, pageable);
    }

    @Override
    public Friend save(Friend friend) {
        return friendJpaRepository.save(FriendEntity.from(friend)).toModel();
    }

    @Override
    public void delete(String nickname) {

    }
    @Override
    public void deleteById(Long friendId) {
        friendJpaRepository.deleteById(friendId);
    }

    @Override
    public Optional<Friend> findMyFriend(Long myId, Long targetId) {
        return friendCustomRepository.findMyFriend(myId, targetId);
    }

}
