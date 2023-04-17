package com.toriumsystems.aidrink.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserIdentityGetDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isGuestAccount;
}
