package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.global.security.service.port.PasswordEncoderHolder;
import lombok.Builder;

public class TestContainer {

    public final PasswordEncoderHolder passwordEncoderHolder;
    public final FakeManagerRepository fakeManagerRepository;

    @Builder
    public TestContainer() {
        this.passwordEncoderHolder = new FakePasswordEncoder();
        this.fakeManagerRepository = new FakeManagerRepository();
    }
}
