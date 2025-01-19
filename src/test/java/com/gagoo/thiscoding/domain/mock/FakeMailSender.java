package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FakeMailSender implements MailSender {

    public Map<String, String> javaMailSender = new HashMap<>();

    @Override
    public Certification send(String email) {
        String code = generateRandomCode();

        javaMailSender.put("subject", "thiscoding 회원가입 메일 인증 코드");
        javaMailSender.put("email", email);
        javaMailSender.put("joinCode", code);

        return new Certification(javaMailSender.get("email"), javaMailSender.get("joinCode"));
    }

    private String generateRandomCode() {
        int code = (int) (Math.random() * 1000000);
        return String.format("%06d", code);
    }

}
