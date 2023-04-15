package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toriumsystems.aidrink.domain.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
