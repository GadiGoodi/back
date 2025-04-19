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
import com.gagoo.thiscoding.domain.maria.user.service.helper.UserFinder;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserFinder userFinder;
    private final SecurityUtils securityUtils;

    /**
     * friendId로 Friend 조회
     * */
    public Friend getByFriendId(Long friendId) {
        return friendRepository.findById(friendId).orElseThrow(() -> new FriendNotFoundException(ErrorCode.FRIEND_NOT_FOUND));
    }

    /**
     * 친구 목록 조회
     */
    @Override
    public Page<FriendInfo> getMyFriends(Pageable pageable) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), FRIEND);
        return friendRepository.findMyFriends(currentUser.getNickname(), customPageable);
    }

    /**
     * 친구 목록에서 친구 검색
     * */
    @Override
    public Page<FriendInfo> getSearchFriends(FriendSearch friendSearch, Pageable pageable) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), FRIEND);
        return friendRepository.searchFriends(currentUser.getNickname(), friendSearch.keyword(), customPageable);
    }

    /**
     * 받은 친구 요청 조회
     */
    @Override
    public Page<FriendInfo> getReceivedFriendRequests(Pageable pageable) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        return friendRepository.findReceivedFriendRequests(currentUser.getNickname(), pageable);
    }

    /**
     * 보낸 친구 요청 조회
     */
    @Override
    public Page<FriendInfo> getSentFriendRequests(Pageable pageable) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        return friendRepository.findSentFriendRequests(currentUser.getNickname(), pageable);
    }

    /**
     * 회원에게 친구 요청 전송
     */
    @Override
    public void friendRequest(String targetUserNickname) {
        User targetUser = userFinder.getByNickname(targetUserNickname);
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        validateUserIsActivated(targetUser);
        validateSelfFriendRequest(targetUserNickname, currentUser.getNickname());

        friendRepository.findMyFriend(currentUser.getId(), targetUser.getId())
            .ifPresentOrElse(friend -> {
                validateFriendRequest(friend, currentUser.getId());
            }, () -> {
                Friend newFriend = Friend.create(targetUser, currentUser);
                friendRepository.save(newFriend);
            });
    }

    /**
     * 친구 요청 전송 시 필요한 검증 로직 모음
     * validateFriend(friend) - 친구 관계인지 확인
     * isCurrentUserReceiver - 이미 상대방에게 받은 요청이 있을 경우 수락 처리
     * isCurrentUserSender - 이미 보낸 요청이 존재하는지 확인
     * 그 외, 유효하지 않은 요청 처리
     * */
    private void validateFriendRequest(Friend friend, Long myId) {
        validateAlreadyFriend(friend);

        if (isCurrentUserReceiver(friend, myId)) {
            friend.accept();
            friendRepository.save(friend);
            return;
        }

        if (isCurrentUserSender(friend, myId)) {
            throw new AlreadyFriendRequestException(ErrorCode.ALREADY_FRIEND_REQUESTED);
        }

        throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
    }

    /**
     * 보낸 친구 요청 취소
     * */
    @Override
    public void cancelFriendRequest(Long friendId) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        Friend friend = getByFriendId(friendId);

        validateAlreadyFriend(friend);
        validateCurrentUserIsSender(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    /**
     * 받은 친구 요청 수락
     */
    @Override
    public void acceptFriendRequest(Long friendId) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        Friend friend = getByFriendId(friendId);

        validateUserIsActivated(friend.getSender());
        validateAcceptFriendRequest(friend, currentUser.getId());

        friend = friend.accept();
        friendRepository.save(friend);
    }

    /**
     * 친구 요청 수락 시, 필요한 검증 로직 모음
     * userIsActivated - 해당 회원 Activate(탈퇴 유무) 확인
     * validateAlreadyFriend - 이미 친구 목록에 존재하는지 확인
     * validateCurrentUserIsReceiver - 내가 받은 요청이 맞는지 확인
     * */
    private void validateAcceptFriendRequest(Friend friend, Long myId){
        validateUserIsActivated(friend.getSender());
        validateAlreadyFriend(friend);
        validateCurrentUserIsReceiver(friend, myId);
    }

    /**
     * 받은 친구 요청 거절
     * */
    @Override
    public void rejectFriendRequest(Long friendId) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());

        Friend friend = getByFriendId(friendId);

        validateAlreadyFriend(friend);
        validateCurrentUserIsReceiver(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    /**
     * 친구 삭제
     */
    @Override
    public void delete(Long friendId) {
        User currentUser = userFinder.getByEmail(securityUtils.getUserEmail());
        Friend friend = getByFriendId(friendId);

        validateIsFriend(friend);
        validateMyFriend(friend, currentUser.getId());

        friendRepository.deleteById(friendId);
    }

    /**
     * 현재 사용자가 해당 친구 관계(Friend)에 포함되어 있지 않으면 예외 발생
     * @param friend
     * @param myId 현재 로그인한 사용자 PK
     * @throws FriendInvalidRequestException - '유효하지 않은 친구 요청입니다.'
     */
    private void validateMyFriend(Friend friend, Long myId) {
        if (!(friend.getReceiver().getId().equals(myId)
            ||friend.getSender().getId().equals(myId)))
            throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
    }

    /**
     * 친구 요청 시, 이미 친구인 경우 예외 발생
     * @param friend
     * @throws FriendAlreadyExistsException - '이미 친구 목록에 존재하는 회원입니다.'
     */
    private void validateAlreadyFriend(Friend friend) {
        if(friend.isFriend())
            throw new FriendAlreadyExistsException(ErrorCode.ALREADY_FRIEND);
    }

    /**
     * 친구가 아닌 경우 예외를 발생.
     * @param friend
     * @throws FriendAlreadyExistsException - '친구를 찾을 수 없습니다.'
     */
    private void validateIsFriend(Friend friend) {
        if(!friend.isFriend())
            throw new FriendNotFoundException(ErrorCode.FRIEND_NOT_FOUND);
    }

    /**
     * 자기 자신에게 친구 요청을 보내려는 경우 예외 발생
     * @param targetUserNickname 요청 대상 닉네임
     * @param myNickname 현재 로그인한 사용자 닉네임
     * @throws FriendInvalidRequestException - '유효하지 않은 친구 요청입니다.'
     */
    private void validateSelfFriendRequest(String targetUserNickname, String myNickname) {
        if (targetUserNickname.equals(myNickname))
            throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
    }

    /**
     * 현재 사용자가 해당 친구 요청의 보낸 사람(sender)이 아닐 경우 예외 발생
     * @param friend
     * @param myId 현재 로그인한 사용자 ID
     * @throws FriendInvalidRequestException - '유효하지 않은 친구 요청입니다.'
     */
    private void validateCurrentUserIsSender(Friend friend, Long myId) {
        if (!isCurrentUserSender(friend, myId))
            throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
    }

    /**
     * 현재 사용자가 해당 친구 요청의 받은 사람(receiver)이 아닐 경우 예외 발생
     * @param friend
     * @param myId 현재 로그인한 사용자 ID
     * @throws FriendInvalidRequestException - '유효하지 않은 친구 요청입니다.'
     */
    private void validateCurrentUserIsReceiver(Friend friend, Long myId){
        if(!isCurrentUserReceiver(friend, myId))
            throw new FriendInvalidRequestException(ErrorCode.INVALID_FRIEND_REQUEST);
    }

    /**
     * 회원의 activated(탈퇴 유무) false인 경우 예외 발생
     * @param user
     * @throws UserNotFoundException - '탈퇴한 회원입니다.'
     * */
    private void validateUserIsActivated(User user) {
            if (!user.isActivated())
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    /**
     * 현재 사용자가 해당 친구 요청의 받은 사람(receiver)인지 확인.
     * @param friend
     * @param myId 현재 로그인한 사용자 PK
     * @return true: 내가 받은 요청일 경우
     */
    private boolean isCurrentUserReceiver(Friend friend, Long myId) {
        return friend.getReceiver().getId().equals(myId);
    }

    /**
     * 현재 사용자가 해당 친구 요청의 보낸 사람(sender)인지 확인.
     * @param friend
     * @param myId 현재 로그인한 사용자 PK
     * @return true: 내가 보낸 요청일 경우
     */
    private boolean isCurrentUserSender(Friend friend, Long myId) {
        return friend.getSender().getId().equals(myId);
    }
}
