package com.toriumsystems.aidrink.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DrinkGetDTO {
    private Long id;

    private String name;

    private String imagePath;
}
