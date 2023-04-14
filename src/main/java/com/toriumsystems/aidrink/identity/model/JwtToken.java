package com.toriumsystems.aidrink.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;

    @ManyToOne
    UserIdentity identity;

    Boolean isRevoked;
}