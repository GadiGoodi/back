package com.gagoo.thiscoding.domain.maria.manager.domain;

import com.gagoo.thiscoding.domain.maria.manager.infrastructure.ManagerEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ManagerNoticesUpdate {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String category;

}
