package com.toriumsystems.aidrink.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.domain.dto.UserIdentityGetDTO;
import com.toriumsystems.aidrink.identity.model.UserIdentity;
import com.toriumsystems.aidrink.identity.repository.UserIdentityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserIdentityService {

    private UserIdentityRepository userIdentityRepository;

    public List<UserIdentityGetDTO> getAllIdentities() {
        var identities = userIdentityRepository.findAll();
        return mapUserIdentitiListToGetDTOList(identities);
    }

    public List<UserIdentityGetDTO> getAllIdentitiesBySerchTerm(String searchTerm) {
        var identities = userIdentityRepository.findByEmailContaining(searchTerm);
        return mapUserIdentitiListToGetDTOList(identities);
    }

    public List<UserIdentityGetDTO> mapUserIdentitiListToGetDTOList(List<UserIdentity> userIdentities) {
        return userIdentities
                .stream()
                .map(this::mapUserIdentityToGetDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<UserIdentityGetDTO> mapUserIdentityToGetDTO(UserIdentity identity) {
        if (identity == null) {
            return Optional.empty();
        }

        var value = UserIdentityGetDTO
                .builder()
                .id(identity.getId())
                .email(identity.getEmail())
                .firstName(identity.getFirstName())
                .lastName(identity.getLastName())
                .build();
        return Optional.of(value);
    }
}
