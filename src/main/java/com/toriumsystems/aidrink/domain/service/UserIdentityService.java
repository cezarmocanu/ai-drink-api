package com.toriumsystems.aidrink.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.domain.dto.AuthSignupDTO;
import com.toriumsystems.aidrink.domain.dto.UserIdentityGetDTO;
import com.toriumsystems.aidrink.domain.model.DrinkCollection;
import com.toriumsystems.aidrink.domain.model.Profile;
import com.toriumsystems.aidrink.domain.repository.DrinkCollectionRepository;
import com.toriumsystems.aidrink.domain.repository.ProfileRepository;
import com.toriumsystems.aidrink.identity.model.UserIdentity;
import com.toriumsystems.aidrink.identity.repository.UserIdentityRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserIdentityService {

    private UserIdentityRepository userIdentityRepository;
    private DrinkCollectionRepository drinkCollectionRepository;
    private ProfileRepository profileRepository;

    public List<UserIdentityGetDTO> getAllIdentities() {
        var identities = userIdentityRepository.findAll();
        return mapUserIdentitiListToGetDTOList(identities);
    }

    public List<UserIdentityGetDTO> getAllIdentitiesBySerchTerm(String searchTerm) {
        var identities = userIdentityRepository.findByEmailContaining(searchTerm);
        return mapUserIdentitiListToGetDTOList(identities);
    }

    @Transactional
    public UserIdentity createUser(AuthSignupDTO dto) {
        var identityBuilder = UserIdentity
                .builder()
                .email(dto.getId())
                .password(dto.getId())
                .isGuestAccount(true)
                .build();
        var identity = userIdentityRepository.save(identityBuilder);

        var drinkCollectionBuilder = DrinkCollection
                .builder()
                .build();
        var drinkCollection = drinkCollectionRepository.save(drinkCollectionBuilder);

        var profileBuilder = Profile
                .builder()
                .identity(identity)
                .drinkCollection(drinkCollection)
                .build();
        var profile = profileRepository.save(profileBuilder);

        identity.setProfile(profile);

        return identity;
    }

    public List<UserIdentityGetDTO> mapUserIdentitiListToGetDTOList(List<UserIdentity> userIdentities) {
        return userIdentities
                .stream()
                .map(this::mapUserIdentityToGetDTO)
                .toList();
    }

    public UserIdentityGetDTO mapUserIdentityToGetDTO(UserIdentity identity) {
        if (identity == null) {
            return null;
        }

        return UserIdentityGetDTO
                .builder()
                .id(identity.getId())
                .email(identity.getEmail())
                .firstName(identity.getFirstName())
                .lastName(identity.getLastName())
                .build();
    }
}
