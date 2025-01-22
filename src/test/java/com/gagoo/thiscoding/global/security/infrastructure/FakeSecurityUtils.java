package com.gagoo.thiscoding.global.security.infrastructure;

import com.gagoo.thiscoding.global.security.service.port.SecurityUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeSecurityUtils implements SecurityUtils {

    private final String email;

    @Override
    public String getUserEmail() {
        return email;
    }

}