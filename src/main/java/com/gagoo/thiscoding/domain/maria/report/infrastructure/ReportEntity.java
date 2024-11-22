package com.gagoo.thiscoding.domain.maria.report.infrastructure;

import com.gagoo.thiscoding.domain.maria.report.domain.ReportType;
import com.gagoo.thiscoding.domain.maria.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "report")
public class ReportEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private String content;

    private String reason;

    @Column(name = "is_finished")
    private boolean isFinished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private UserEntity reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private UserEntity reported;
}
