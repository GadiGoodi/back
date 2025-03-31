package com.gagoo.thiscoding.domain.maria.friend.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gagoo.thiscoding.domain.maria.friend.controller.port.FriendService;
import com.gagoo.thiscoding.domain.maria.friend.controller.request.FriendSearch;
import com.gagoo.thiscoding.domain.maria.friend.controller.response.FriendInfoResponse;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.paging.dto.CustomPageDto;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-page/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    /**
     * 친구 목록 조회
     * */
    @GetMapping
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CustomPageDto<FriendInfoResponse>> getMyFriends(Pageable pageable) {
        Page<FriendInfoResponse> result = friendService.getMyFriends(pageable).map(FriendInfoResponse::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(result));
    }

    /**
     * 받은 친구 요청 목록 조회
     * */
    @GetMapping("/requests/received")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CustomPageDto<FriendInfoResponse>> getReceivedFriendRequests(Pageable pageable) {
        Page<FriendInfoResponse> result = friendService.getReceivedFriendRequests(pageable).map(FriendInfoResponse::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(result));
    }

    /**
     * 보낸 친구 요청 목록 조회
     * */
    @GetMapping("/requests/sent")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CustomPageDto<FriendInfoResponse>> getSentFriendRequests(Pageable pageable) {
        Page<FriendInfoResponse> result = friendService.getSentFriendRequests(pageable).map(FriendInfoResponse::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(result));
    }


    /**
     * 친구 목록에서 친구 검색
     * */
    @GetMapping("/search")
    @ConvertToOneBase
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<CustomPageDto<FriendInfoResponse>> getSearchFriends(@ModelAttribute
        FriendSearch friendSearch, Pageable pageable) {
        Page<FriendInfoResponse> result = friendService.getSearchFriends(friendSearch, pageable).map(FriendInfoResponse::from);
        return ResponseEntity
            .ok()
            .body(CustomPageDto.of(result));
    }

    /**
     * 회원에게 친구 요청 전송
     * */
    @PutMapping("/requests/sent/{nickname}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> FriendRequest(@PathVariable String nickname) {
        friendService.friendRequest(nickname);
        return ResponseEntity
            .ok("요청 완료");
    }

    /**
     * 회원에게 보낸 친구 요청 취소
     * */
    @DeleteMapping("/requests/sent/{friendId}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> cancelFriendRequest(@PathVariable Long friendId) {
        friendService.cancelFriendRequest(friendId);
        return ResponseEntity
            .ok("취소 완료");
    }

    /**
     * 받은 친구 요청 수락
     * */
    @PutMapping("/requests/received/{friendId}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long friendId) {
        friendService.acceptFriendRequest(friendId);
        return ResponseEntity
            .ok("수락 완료");
    }

    /**
     * 받은 친구 요청 거절
     * */
    @DeleteMapping("/requests/received/{friendId}")
    @AuthorizationRequired(value = Role.USER, status = OK)
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long friendId) {
        friendService.rejectFriendRequest(friendId);
        return ResponseEntity
            .ok("거절 완료");
    }

}
