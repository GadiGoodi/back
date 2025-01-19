package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;

import java.util.HashMap;
import java.util.Map;

public class FakeMailSender implements MailSender {

    public Map<String, String> javaMailSender = new HashMap<>();

    @Override
    public Certification send(String email) {
        String code = "123456";

        javaMailSender.put("subject", "thiscoding 회원가입 메일 인증 코드");
        javaMailSender.put("email", email);
        javaMailSender.put("joinCode", "123456");

        return new Certification(javaMailSender.get("email"), javaMailSender.get("joinCode"));
    }
}
