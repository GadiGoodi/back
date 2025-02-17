package com.gagoo.thiscoding.domain.auth.service;

import com.gagoo.thiscoding.domain.auth.service.port.PasswordEncoderHolder;
import com.gagoo.thiscoding.domain.auth.service.port.PasswordService;
import com.gagoo.thiscoding.domain.maria.user.service.exception.PasswordNotEqualException;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoderHolder passwordEncoder;

    /**
     * 로그인할 때 입력한 비밀번호와
     * db에 저장된 암호화된 비밀번호가
     * 일치하는지 확인
     */
    @Override
    public void matchPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new PasswordNotEqualException(ErrorCode.PASSWORD_NOT_EQUAL);
        }
    }
}