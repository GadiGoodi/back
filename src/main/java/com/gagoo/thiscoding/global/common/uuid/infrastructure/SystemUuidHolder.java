package com.gagoo.thiscoding.global.common.uuid.infrastructure;

import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {
    public String random() {
        return UUID.randomUUID().toString();
    }
}