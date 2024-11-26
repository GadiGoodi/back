package com.gagoo.thiscoding.domain.maria.user.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.dto.Certification;

public interface MailSender {

    Certification send(String email);
}
