package com.toriumsystems.aidrink.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class AuthSignupDTO {
    @NotBlank
    private String id;
}
