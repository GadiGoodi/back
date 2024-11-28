package com.gagoo.thiscoding.domain.maria.coderoom.infrastructure;

import com.gagoo.thiscoding.domain.maria.coderoom.domain.CodeRoom;
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

    public CodeRoom toModel(){
        return CodeRoom.builder()
            .id(this.id)
            .uuid(this.uuid)
            .title(this.title)
            .content(this.content)
            .language(this.language)
            .headCount(this.headCount)
            .build();
    }
    public static CodeRoomEntity from(CodeRoom codeRoom){
        CodeRoomEntity entity = new CodeRoomEntity();
        entity.id = codeRoom.getId();
        entity.uuid = codeRoom.getUuid();
        entity.title = codeRoom.getTitle();
        entity.content = codeRoom.getContent();
        entity.language = codeRoom.getLanguage();
        entity.headCount = codeRoom.getHeadCount();
        return entity;

    }
}
