package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    public String send(String email) {
        String subject = ("thiscoding 회원가입 메일 인증 코드");

        SimpleMailMessage message = new SimpleMailMessage();

        String joinCode = generateAuthCode();

        message.setTo(email);
        message.setSubject(subject);
        message.setText(formatAuthCodeMessage(joinCode));

        javaMailSender.send(message);

        return joinCode;
    }

    /**
     * 6자리 랜덤 숫자 생성
     * @return String secureRandom
     */
    public static String generateAuthCode() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(900000) + 100000);
    }

    /**
     * 이메일로 전송할 인증코드 메시지 포매터
     * @param authCode 6자리 랜덤 숫자
     * @return 메일 인증 코드 메시지
     */
    public static String formatAuthCodeMessage(String authCode) {
        return "메일 인증 코드는 \"" + authCode + "\" 입니다";
    }


}
