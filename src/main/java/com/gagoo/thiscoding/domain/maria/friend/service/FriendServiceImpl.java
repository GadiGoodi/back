package com.gagoo.thiscoding.domain.maria.friend.service;

import static com.gagoo.thiscoding.global.paging.PageSize.FRIEND;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.friend.controller.port.FriendService;
import com.gagoo.thiscoding.domain.maria.friend.controller.request.FriendSearch;
import com.gagoo.thiscoding.domain.maria.friend.domain.Friend;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.AlreadyFriendRequestException;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.FriendAlreadyExistsException;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.FriendInvalidRequestException;
import com.gagoo.thiscoding.domain.maria.friend.service.Exception.FriendNotFoundException;
import com.gagoo.thiscoding.domain.maria.friend.service.dto.FriendInfo;
import com.gagoo.thiscoding.domain.maria.friend.service.port.FriendRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    public Friend getByFriendId(Long friendId) {
        return friendRepository.findById(friendId).orElseThrow(() -> new FriendNotFoundException(ErrorCode.FRIEND_NOT_FOUND));
    }

    /**
     * 내 친구 목록 조회
     */
    @Override
    public Page<FriendInfo> getMyFriends(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), FRIEND);
        return friendRepository.findMyFriends(securityUtils.getUserNickname(), customPageable);
    }

    /**
     * 내 친구 검색
     * */
    @Override
    public Page<FriendInfo> getSearchFriends(FriendSearch friendSearch, Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), FRIEND);
        return friendRepository.searchFriends(securityUtils.getUserNickname(), friendSearch.keyword(), customPageable);
    }

    /**
     * 받은 친구 요청 조회
     */
    @Override
    public Page<FriendInfo> getReceivedFriendRequests(Pageable pageable) {
        return friendRepository.findReceivedFriendRequests(securityUtils.getUserNickname(), pageable);
    }

    /**
     * 보낸 친구 요청 조회
     */
    @Override
    public Page<FriendInfo> getSentFriendRequests(Pageable pageable) {
        return friendRepository.findSentFriendRequests(securityUtils.getUserNickname(), pageable);
    }

    /**
     * 회원에게 친구 요청 전송
     */
    @Override
    public void friendRequest(String targetUserNickname) {
        User targetUser = userRepository.getByNickname(targetUserNickname);
        User currentUser = userRepository.getByNickname(securityUtils.getUserNickname());

        friendRepository.findMyFriend(currentUser.getId(), targetUser.getId())
            .ifPresentOrElse(friend -> {
                if (friend.getReceiver().getId().equals(currentUser.getId())) {
                    friend.accept();
                    friendRepository.save(friend);
                    return;
                }

                validateSelfFriendRequest(targetUserNickname, currentUser.getNickname());
                validateAlreadyFriend(friend);
                validateMySentRequest(friend, currentUser.getId());

            }, () -> {
                Friend newFriend = Friend.create(targetUser, currentUser);
                friendRepository.save(newFriend);
            });

    }

    /**
     * 보낸 친구 요청 취소
     * */
    @Override
    public void cancelFriendRequest(Long friendId) {
        User currentUser = userRepository.getByNickname(securityUtils.getUserNickname());
        Friend friend = getByFriendId(friendId);

        validateAlreadyFriend(friend);
        validateMySentRequest(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    /**
     * 받은 친구 요청 수락
     */
    @Override
    public void acceptFriendRequest(Long friendId) {
        User currentUser = userRepository.getByNickname(securityUtils.getUserNickname());

        Friend friend = getByFriendId(friendId);

        validateAlreadyFriend(friend);
        validateMyReceiveRequest(friend, currentUser.getId());

        friend = friend.accept();
        friendRepository.save(friend);
    }
    /**
     * 받은 친구 요청 거절
     * */
    @Override
    public void rejectFriendRequest(Long friendId) {
        User currentUser = userRepository.getByNickname(securityUtils.getUserNickname());

        Friend friend = getByFriendId(friendId);
        validateAlreadyFriend(friend);

        validateMyReceiveRequest(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    /**
     * 친구 삭제
     */
    @Override
    public void delete(Long friendId) {
        User currentUser = userRepository.getByNickname(securityUtils.getUserNickname());
        Friend friend = getByFriendId(friendId);

        validateNotFriend(friend);
        validateMyFriend(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    // 해당 Friend 데이터가 내 친구 목록에 해당되는지 확인
    private void validateMyFriend(Friend friend, Long myId) {
        if (!(friend.getReceiver().getId().equals(myId)
            ||friend.getSender().getId().equals(myId)))
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
    }

    // 이미 친구인 경우 예외 발생
    private void validateAlreadyFriend(Friend friend) {
        if(friend.isFriend())
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
    }

    // 친구가 아닌 경우 예외 발생
    private void validateNotFriend(Friend friend) {
        if(!friend.isFriend()){
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
        }
    }

    // 자기 자신에게 요청을 보내는 경우
    private void validateSelfFriendRequest(String targetUserNickname, String myNickname) {
        if (targetUserNickname.equals(myNickname)) {
            throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
        }
    }

    // 내가 보낸 요청이 아니면 예외 발생
    private void validateMySentRequest(Friend friend, Long myId) {
        if (!friend.getSender().getId().equals(myId))
            throw new AlreadyFriendRequestException(ErrorCode.ALREADY_FRIEND_REQUESTED);
    }

    // 내가 받은 요청이 아니면 예외 발생
    private void validateMyReceiveRequest(Friend friend, Long myId){
        if(!friend.getReceiver().getId().equals(myId)) {
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
        }

    }

}
