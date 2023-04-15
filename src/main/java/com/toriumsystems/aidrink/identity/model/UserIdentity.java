package com.toriumsystems.aidrink.identity.model;

import java.util.List;

import com.toriumsystems.aidrink.domain.model.Profile;
import com.toriumsystems.aidrink.identity.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserIdentity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String firstName = "";

    @Builder.Default
    private String lastName = "";

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Builder.Default
    private Boolean isGuestAccount = true;

    @OneToMany(mappedBy = "identity")
    private List<JwtToken> tokens;

    @OneToOne(mappedBy = "identity")
    private Profile profile;
}
