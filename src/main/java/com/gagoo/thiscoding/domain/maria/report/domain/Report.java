package com.gagoo.thiscoding.domain.maria.report.domain;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Getter
public class Report {

    private Long id;
    private User reporterId;
    private User reportedId;
    private Long targetId;
    private ReportType type;
    private String content;
    private String reason;
    private boolean isFinished;
    private LocalDateTime createDate;

}
