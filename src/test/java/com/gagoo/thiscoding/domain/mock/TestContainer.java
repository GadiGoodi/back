package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeStore;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import com.gagoo.thiscoding.global.security.service.port.PasswordEncoderHolder;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final JoinCodeStore joinCodeStore;
    public final PasswordEncoderHolder passwordEncoderHolder;
    public final FakeManagerRepository fakeManagerRepository;

    @Builder
    public TestContainer() {
        this.mailSender = new FakeMailSender();
        this.joinCodeStore = new FakeJoinCodeStore();
        this.passwordEncoderHolder = new FakePasswordEncoder();
        this.fakeManagerRepository = new FakeManagerRepository();
    }
}