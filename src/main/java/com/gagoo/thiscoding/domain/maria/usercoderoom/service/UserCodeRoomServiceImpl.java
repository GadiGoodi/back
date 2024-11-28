package com.gagoo.thiscoding.domain.maria.usercoderoom.service;


import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class UserCodeRoomServiceImpl implements UserCodeRoomService {
    private final UserCodeRoomRepository userCodeRoomRepository;


}
