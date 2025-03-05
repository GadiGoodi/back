package com.gagoo.thiscoding.domain.maria.coderoom.service;

import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.ParticipationService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.response.ParticipatingCodeRoomResponse;
import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
import com.gagoo.thiscoding.domain.maria.coderoom.infrastructure.jpa.CodeRoomCustomRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.CodeNotFoundException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.PageSize;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gagoo.thiscoding.domain.maria.coderoom.domain.contants.Capacity.MIN_CAPACITY;

@Service
@Builder
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final CodeRoomRepository codeRoomRepository;
    private final UserCodeRoomRepository userCodeRoomRepository;
    private final CodeRoomCustomRepository codeRoomCustomRepository;
    private final SecurityUtils securityUtils;

    /**
     * 참여 중인 코드방 전체 조회
     * @return 참여 중인 ParticipationsResponse 페이지
     */
    @Override
    public Page<ParticipatingCodeRoomResponse> getParticipations(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), PageSize.CODEROOM);

        Page<UserCodeRoom> userCodeRoomPage = codeRoomCustomRepository
                .findAllByEmailAndIsActivatedTrue(securityUtils.getUserEmail(), customPageable);

        return userCodeRoomPage.map(userCodeRoom -> ParticipatingCodeRoomResponse.from(userCodeRoom, codeRoomCustomRepository.findUserListByUserCodeRoom(userCodeRoom)));
    }

    /**
     * (참여 중인) 코드방 입/퇴장
     * @param id
     */
    @Override
    public void accessUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = userCodeRoomRepository.getById(id);

        userCodeRoomRepository.save(userCodeRoom.access());
    }

    /**
     * (참여 중인) 코드방 탈퇴
     * @param id
     */
    @Override
    public void leaveUserCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = userCodeRoomRepository.getById(id);
        userCodeRoomRepository.deleteById(id);

        CodeRoom codeRoom = codeRoomRepository.findById(userCodeRoom.getCodeRoom().getId()).orElseThrow(
                () -> new CodeNotFoundException(ErrorCode.CODE_NOT_FOUND)
        );

        if(codeRoom.getHeadCount() > MIN_CAPACITY) {
            codeRoomRepository.save(codeRoom.exit());
        } else {
            codeRoomRepository.deleteById(codeRoom.getId());
        }
    }
}
