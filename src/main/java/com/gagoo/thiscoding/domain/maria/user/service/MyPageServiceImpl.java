package com.gagoo.thiscoding.domain.maria.user.service;

import com.gagoo.thiscoding.domain.maria.user.controller.port.MyPageService;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardCustomRepository;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final BoardCustomRepository boardCustomRepository;

    @Override
    public Boolean isTop10() {
        User currentUser = userRepository.getByEmail(SecurityUtils.getUserEmail());

        List<Long> top10Users = boardCustomRepository.getTop10Users();
        boolean isTop10 = validateTop10(currentUser, top10Users);

        return isTop10;
    }

    /**
     * top10 유저인지 확인
     */
    private boolean validateTop10(User currentUser, List<Long> top10Users) {
        for (Long top10User : top10Users) {
            if (top10User == currentUser.getId()) {
                return true;
            }
        }

        return false;
    }

}
