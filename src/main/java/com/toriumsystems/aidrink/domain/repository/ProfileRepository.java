package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toriumsystems.aidrink.domain.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p WHERE p.identity.id = :indentityId")
    Profile findByIdentityId(@Param("indentityId") Long indentityId);
}
