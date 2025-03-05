package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeUuidHolder implements UuidHolder {
    private final String uuid;

    @Override
    public String random() {
        return this.uuid;
    }
}