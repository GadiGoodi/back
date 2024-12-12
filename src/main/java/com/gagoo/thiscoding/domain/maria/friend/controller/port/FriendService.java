package com.gagoo.thiscoding.domain.maria.friend.controller.port;

import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.domain.dto.FriendRequest;
import java.util.List;

public interface FriendService {

    Friend Create(FriendRequest friendRequest);

    List<Friend> GetFriendList(Long userId);

    Long Delete(FriendRequest friendRequest);
}
