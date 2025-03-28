package com.gagoo.thiscoding.domain.maria.friend.infrastructure.jpa;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendCustomRepository {
    Page<FriendInfo> searchMyFriends(String currentUser, String keyword, Pageable pageable);

    Optional<Friend> findMyFriend(Long myId, Long targetId);

    Page<FriendInfo> findMyFriends(String nickname, Pageable pageable);

    Page<FriendInfo> findSentFriendRequests(String nickname, Pageable pageable);

    Page<FriendInfo> findReceivedFriendRequests(String nickname, Pageable pageable);

}
