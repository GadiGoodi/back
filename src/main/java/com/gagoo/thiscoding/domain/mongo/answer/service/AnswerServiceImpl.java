package com.gagoo.thiscoding.domain.mongo.answer.service;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.answer.controller.port.AnswerService;
import com.gagoo.thiscoding.domain.mongo.answer.domain.Answer;
import com.gagoo.thiscoding.domain.mongo.answer.domain.dto.AnswerCreate;
import com.gagoo.thiscoding.domain.mongo.answer.service.port.AnswerRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.security.SecurityUtils;
import com.gagoo.thiscoding.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Answer create(AnswerCreate answerCreate) {
        User currentUser = getByEmail(SecurityUtils.getUserEmail());
        Answer answer = Answer.create(currentUser, answerCreate);

        return answerRepository.save(answer);
    }

    private User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
