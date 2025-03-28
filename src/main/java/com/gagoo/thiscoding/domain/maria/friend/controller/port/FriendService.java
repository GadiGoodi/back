package com.gagoo.thiscoding.domain.maria.friend.controller.port;

import com.gagoo.thiscoding.domain.maria.friend.controller.request.FriendSearch;
import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendService {

    Page<FriendInfo> getMyFriends(Pageable pageable);

    Page<FriendInfo> getReceivedFriendRequests(Pageable pageable);

    Page<FriendInfo> getSentFriendRequests(Pageable pageable);

    Page<FriendInfo> getSearchFriends(FriendSearch friendSearch, Pageable pageable);

    void friendRequest(String receiverNickname);

    void delete(Long friendId);

    void cancelFriendRequest(Long friendId);

    void acceptFriendRequest(Long friendId);

    void rejectFriendRequest(Long friendId);
}

