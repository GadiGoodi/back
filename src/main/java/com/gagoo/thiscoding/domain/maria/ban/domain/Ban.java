package com.gagoo.thiscoding.domain.maria.ban.domain;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ban {

    private Long id;
    private User user;
    private String reason;
    private int banCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
