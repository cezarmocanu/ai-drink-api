package com.toriumsystems.aidrink.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthNewIdentityDTO {
    private UserIdentityGetDTO identity;
    private String token;
}
