package com.toriumsystems.aidrink.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toriumsystems.aidrink.identity.model.JwtToken;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    JwtToken findByTokenAndIsRevokedFalse(String token);

}
