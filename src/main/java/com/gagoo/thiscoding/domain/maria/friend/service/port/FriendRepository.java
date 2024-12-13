package com.gagoo.thiscoding.domain.maria.friend.service.port;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.domain.dto.FriendRequest;
import java.util.List;
import java.util.Optional;

public interface FriendRepository {


    Optional<Friend> findById(Long userId);

    Friend save(Friend friend);

    void delete(FriendRequest friendRequest);

    List<Friend> findAllById(Long userId);

    boolean existsByReceiverIdAndSenderId(Long userId, Long friendId);
}
