package com.gagoo.thiscoding.domain.maria.friend.service;

import com.gagoo.thiscoding.domain.maria.friend.controller.port.FriendService;
import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.domain.dto.FriendRequest;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.FriendAlreadyExistsException;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.FriendNotFoundException;
import com.gagoo.thiscoding.domain.maria.friend.service.port.FriendRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     * 해당 회원이 존재하는지 확인
     * */
    private User userGetById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateUserExistence(Long userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }


    /**
     * 해당 회원이 이미 친구인 경우 예외
     * @param receiverId
     * @param senderId
     */
    private void validateFriendDoesNotExist(Long receiverId, Long senderId) {
        if (friendRepository.existsByReceiverIdAndSenderId(receiverId, senderId)) {
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
        }
    }

    /**
     * 해당 회원이 친구가 아닐 경우 예외
     * @param receiverId
     * @param senderId
     */
    private void validateFriendExists(Long receiverId, Long senderId) {
        if (!friendRepository.existsByReceiverIdAndSenderId(receiverId, senderId)) {
            throw new FriendNotFoundException(ErrorCode.FRIEND_NOT_FOUND);
        }
    }

    /**
     * 친구 추가
     * @param friendRequest
     * @return 추가된 친구 정보
     */
    @Override
    public Friend create(FriendRequest friendRequest) {
        validateFriendDoesNotExist(friendRequest.getReceiverId(), friendRequest.getSenderId());

        User sender = userGetById(friendRequest.getSenderId());
        User receiver = userGetById(friendRequest.getReceiverId());

        Friend friend = Friend.from(sender, receiver);
        return friendRepository.save(friend);
    }

    /**
     * 친구 목록 조회
     * @param userId
     * @return
     */
    @Override
    public List<Friend> getFriendList(Long userId) {
        validateUserExistence(userId);
        return friendRepository.findAllById(userId);
    }

    /**
     * 친구 삭제
     * @param friendRequest
     * @return
     */
    @Override
    public Long delete(FriendRequest friendRequest) {
        validateUserExistence(friendRequest.getReceiverId());
        validateUserExistence(friendRequest.getSenderId());
        validateFriendExists(friendRequest.getReceiverId(), friendRequest.getSenderId());

        friendRepository.delete(friendRequest);
        return friendRequest.getSenderId();
    }
}
