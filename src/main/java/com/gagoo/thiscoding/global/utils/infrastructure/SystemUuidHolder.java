package com.gagoo.thiscoding.global.utils.infrastructure;

import com.gagoo.thiscoding.global.utils.service.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {
    public UUID random() {
        return UUID.randomUUID();
    }
}