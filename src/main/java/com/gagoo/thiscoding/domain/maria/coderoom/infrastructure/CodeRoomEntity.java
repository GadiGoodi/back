package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "code_room")
public class CodeRoomEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_room_id")
    private Long id;

    private UUID uuid;

    private String title;

    private String content;

    private String language;

    @Column(name = "head_count")
    private int headCount;
}
