package com.toriumsystems.aidrink.identity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toriumsystems.aidrink.identity.model.UserIdentity;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {

    UserIdentity findByEmail(String email);

    List<UserIdentity> findByEmailContaining(String searchTerm);
}
