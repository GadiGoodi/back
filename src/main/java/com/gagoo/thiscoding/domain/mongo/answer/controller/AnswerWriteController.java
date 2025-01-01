package com.gagoo.thiscoding.domain.mongo.answer.controller;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.domain.mongo.answer.controller.port.AnswerService;
import com.gagoo.thiscoding.domain.mongo.answer.domain.dto.AnswerCreate;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerWriteController {

    private final AnswerService answerService;

    @PostMapping
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN}, status = OK)
    public ResponseEntity<Void> create(@RequestBody AnswerCreate answerCreate) {
        answerService.create(answerCreate);

        return ResponseEntity.status(CREATED).build();
    }

}
