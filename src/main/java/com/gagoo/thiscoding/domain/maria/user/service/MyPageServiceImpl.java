package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.domain.UserCodeRoom;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Builder
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
    private final UserCodeRoomRepository userCodeRoomRepository;

    @Override
    public Page getUserCodeRooms(Long id, Pageable pageable) {
        return userCodeRoomRepository.findByUserId(id, pageable);
    };

    public UserCodeRoom getUserCodeRoom(Long id) {
        return userCodeRoomRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @Override
    @Transactional
    public UserCodeRoom acceptCodeRoom(Long id) {
        UserCodeRoom userCodeRoom = getUserCodeRoom(id).accept();
        userCodeRoomRepository.save(userCodeRoom);

        return userCodeRoom;
    }

    @Override
    @Transactional
    public void cancelCodeRoom(Long userCodeRoomId) {
        userCodeRoomRepository.delete(userCodeRoomId);
    }


}
