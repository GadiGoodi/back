package com.gagoo.thiscoding.domain.maria.usercoderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.response.ParticipationsResponse;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.dto.InviteCodeRoom;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.ipml.ParticipationsRepositoryCustom;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.CodeNotFoundException;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.exception.OverCapacityException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.infrastructure.exception.UserCodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PagingProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCodeRoomServiceImpl implements UserCodeRoomService {

    private final UserCodeRoomRepository userCodeRoomRepository;
    private final UserRepository userRepository;
    private final CodeRoomRepository codeRoomRepository;
    private final ParticipationsRepositoryCustom participationsRepositoryCustom;

    public UserCodeRoom getUserCodeRoomByCodeRoomAndUserEmail(Long codeRoomId, String userEmail) {
        return userCodeRoomRepository.findByCodeRoomIdAndUserEmail(codeRoomId, userEmail)
                .orElseThrow(() -> new UserCodeRoomNotFoundException(
                        ErrorCode.USER_CODE_ROOM_NOT_FOUND
                ));
    }

    /**
     * 초대된 코드방 인원수 체크
     */
    public void validateCapacity(CodeRoom codeRoom) {
        final int MAX_CAPACITY = 6;
        if (codeRoom.getHeadCount() >= MAX_CAPACITY) {
            throw new OverCapacityException(ErrorCode.OVER_CAPACITY_CODE_ROOM);
        }
    }

    /**
     * 코드방 참여 생성
     * @param userId
     * @param roomId
     * @return 생성한 UserCodeRoom
     */
    @Override
    public boolean createUserCodeRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
        CodeRoom codeRoom = codeRoomRepository.findById(roomId).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );
        UserCodeRoom userCodeRoom = UserCodeRoom.create(user, codeRoom);
        if(userCodeRoomRepository.save(userCodeRoom) != null) {
            return true;
        }
        return false;
    }

    /**
     * 초대된 코드방 전체 조회
     */
    @Override
    public Page<InviteCodeRoom> getUserCodeRooms(int page) {
        Pageable pageable = PagingProcessor.toPageable(page, PageSize.CODEROOM);
        return userCodeRoomRepository
                .findByEmail(SecurityUtils.getUserEmail(), pageable)
                .map(InviteCodeRoom::from);
    }

    /**
     * 초대된 코드방 수락
     */
    @Override
    @Transactional
    public void acceptCodeRoom(Long codeRoomId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUserEmail(codeRoomId, SecurityUtils.getUserEmail());
        validateCapacity(userCodeRoom.getCodeRoom());
        userCodeRoomRepository.save(userCodeRoom.accept());
    }

    /**
     * 초대된 코드방 거절
     */
    @Override
    @Transactional
    public void cancelCodeRoom(Long codeRoomId) {
        UserCodeRoom userCodeRoom = getUserCodeRoomByCodeRoomAndUserEmail(codeRoomId, SecurityUtils.getUserEmail());
        userCodeRoomRepository.delete(userCodeRoom);
    }

    /**
     * 코드방 조회
     * @param id
     * @return 조회한 UserCodeRoom
     */
    public UserCodeRoom getUserCodeRoom(Long id) {
        return userCodeRoomRepository.findById(id)
                .orElseThrow(() ->new UserCodeRoomNotFoundException(ErrorCode.USER_CODE_ROOM_NOT_FOUND));
    };

    /**
     * 참여 중인 코드방 전체 조회
     * @param page
     * @return 참여 중인 ParticipationsResponse 페이지
     */
    @Override
    public Page<ParticipationsResponse> getParticipations(int page) {
        Pageable pageable = PagingProcessor.toPageable(page, PageSize.CODEROOM);

        Page<UserCodeRoom> userCodeRoomPage = participationsRepositoryCustom
                .findAllByEmailAndIsActivatedTrue(SecurityUtils.getUserEmail(), pageable);

        return userCodeRoomPage.map(userCodeRoom -> ParticipationsResponse.from(userCodeRoom, participationsRepositoryCustom.findUserListByUserCodeRoom(userCodeRoom)));
    }

    /**
     * (참여 중인) 코드방 입/퇴장
     * @param id
     */
    @Override
    public void accessUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = getUserCodeRoom(id);
        userCodeRoomRepository.save(userCodeRoom.access());
    }

    /**
     * (참여 중인) 코드방 탈퇴
     * @param id
     */
    @Override
    public void leaveUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = getUserCodeRoom(id);
        userCodeRoomRepository.delete(userCodeRoom);

        CodeRoom codeRoom = codeRoomRepository.findById(userCodeRoom.getCodeRoom().getId()).orElseThrow(
                () -> new CodeNotFoundException(ErrorCode.CODE_NOT_FOUND)
        );

        if(codeRoom.getHeadCount() > 1) {
            codeRoomRepository.save(codeRoom.exit());
        } else {
            codeRoomRepository.deleteById(codeRoom.getId());
        }
    }
}
