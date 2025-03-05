package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.ParticipationService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.ParticipatingCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.service.exception.CodeRoomNotFoundException;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.exception.NotUserCodeRoomParticipantException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MIN_CAPACITY;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final CodeRoomRepository codeRoomRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final CodeRoomCustomRepository codeRoomCustomRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    /**
     * 참여 중인 코드방 전체 조회
     * @return 참여 중인 ParticipationsResponse 페이지
     */
    @Override
    public Page<ParticipatingCodeRoomResponse> getParticipations(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), PageSize.CODEROOM);
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());

        Page<UserCodeRoom> userCodeRoomPage = codeRoomCustomRepository
                .findAllUserCodeRoomByUser(currentUser, customPageable);

        return userCodeRoomPage.map(userCodeRoom -> ParticipatingCodeRoomResponse.from(userCodeRoom, codeRoomCustomRepository.findUserListByUserCodeRoom(userCodeRoom)));
    }

    /**
     * (참여 중인) 코드방 입/퇴장
     * @param id
     */
    @Override
    public boolean accessUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = userCodeRoomRepository.getById(id);

        validateUser(userCodeRoom);

        if(userCodeRoomRepository.save(userCodeRoom.access()) == null) {
            return false;
        }

        return true;
    }

    /**
     * (참여 중인) 코드방 탈퇴
     * @param id
     */
    @Override
    public boolean leaveUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = userCodeRoomRepository.getById(id);

        validateUser(userCodeRoom);

        userCodeRoomRepository.deleteById(userCodeRoom.getId());

        CodeRoom codeRoom = codeRoomRepository.findById(userCodeRoom.getCodeRoom().getId()).orElseThrow(
                () -> new CodeRoomNotFoundException(ErrorCode.CODE_ROOM_NOT_FOUND)
        );

        if(codeRoom.getHeadCount() > MIN_CAPACITY) {
            codeRoomRepository.save(codeRoom.exit());
        } else {
            codeRoomRepository.deleteById(codeRoom.getId());
        }

        return true;
    }

    /**
     * 코드방 참여자 여부 확인
     */
    private void validateUser(UserCodeRoom userCodeRoom) {
        User currentUser = userRepository.getByEmail(securityUtils.getUserEmail());
        if(!userCodeRoom.getUser().equals(currentUser)) {
            throw new NotUserCodeRoomParticipantException(ErrorCode.NOT_USER_CODE_ROOM_PARTICIPANT);
        }
    }
}
