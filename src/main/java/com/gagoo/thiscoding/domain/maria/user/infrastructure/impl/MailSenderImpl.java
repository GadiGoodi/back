package com.gagoo.thiscoding.domain.maria.user.infrastructure.impl;

import com.gagoo.thiscoding.domain.maria.user.dto.Certification;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private static final String SERVICE_NAME = "thiscoding";
    private static final char[] VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(SecureRandom::new);

    private final JavaMailSender javaMailSender;

    /**
     * 6자리 숫자 인증 코드 전송
     */
    @Override
    public Certification sendSignUpCode(String email) {
        String subject = String.format("%s 회원가입 메일 인증 코드", SERVICE_NAME);
        String joinCode = generateSignUpCode();

        sendEmail(email, subject, joinCode);

        return new Certification(email, joinCode);
    }

    /**
     * 8자리 문자열 비밀번호 변경 인증코드
     */
    @Override
    public Certification sendResetPasswordCode(String email) {
        String subject = String.format("%s 비밀번호 변경 인증 코드", SERVICE_NAME);
        String joinCode = generateResetPasswordCode();

        sendEmail(email, subject, joinCode);

        return new Certification(email, joinCode);
    }

    /**
     * 이메일 전송
     */
    private void sendEmail(String email, String subject, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(formatAuthCodeMessage(code));
        javaMailSender.send(message);
    }

    /**
     * 6자리 랜덤 숫자 생성
     */
    public static String generateSignUpCode() {
        SecureRandom secureRandom = SECURE_RANDOM.get();
        return String.format("%06d", secureRandom.nextInt(1000000));
    }

    /**
     * 8자리 랜덤 문자열 생성
     */
    public static String generateResetPasswordCode() {
        SecureRandom random = SECURE_RANDOM.get();
        char[] result = new char[8];

        for (int i = 0; i < result.length ; i++) {
            result[i] = VALID_CHARS[random.nextInt(VALID_CHARS.length)];
        }

        return new String(result);
    }

    /**
     * 이메일로 전송할 인증코드 메시지 포매터
     */
    public static String formatAuthCodeMessage(String authCode) {
        return String.format("인증 코드는 \"%s\" 입니다", authCode);
    }
}