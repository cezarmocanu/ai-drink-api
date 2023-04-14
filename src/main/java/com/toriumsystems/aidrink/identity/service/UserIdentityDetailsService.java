package com.toriumsystems.aidrink.identity.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.identity.model.UserIdentity;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.repository.UserIdentityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserIdentityDetailsService implements UserDetailsService {

    private UserIdentityRepository userIdentityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserIdentity identity = userIdentityRepository.findByEmail(username);

        if (identity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserIdentityDetails(identity);
    }

    public UserIdentityDetails loadUserById(Long identityId) throws Exception {
        Optional<UserIdentity> identity = userIdentityRepository.findById(identityId);

        if (identity.isEmpty()) {
            throw new Exception(String.format("Could not find identity for id %s.", identityId));
        }

        return new UserIdentityDetails(identity.get());
    }
}