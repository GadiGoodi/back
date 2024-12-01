package com.gagoo.thiscoding.domain.maria.manager.controller.response;

import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ManagerNoticesUpdate {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String category;

    public ManagerEntity toEntity(){
        return ManagerEntity.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }

}
