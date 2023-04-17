package com.toriumsystems.aidrink.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.domain.dto.AuthIdentityResponseDTO;
import com.toriumsystems.aidrink.domain.dto.AuthIdentityRequestDTO;
import com.toriumsystems.aidrink.domain.dto.UserIdentityGetDTO;
import com.toriumsystems.aidrink.domain.model.Profile;
import com.toriumsystems.aidrink.domain.repository.ProfileRepository;
import com.toriumsystems.aidrink.domain.types.DrinkPageConfig;
import com.toriumsystems.aidrink.identity.model.UserIdentity;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.repository.UserIdentityRepository;
import com.toriumsystems.aidrink.identity.service.JwtTokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIdentityService {

    private final UserIdentityRepository userIdentityRepository;
    // TODO add this to profile service
    private final ProfileRepository profileRepository;
    private final JwtTokenService jwtService;
    private final DrinkService drinkService;

    private Random random = new Random();

    public List<UserIdentityGetDTO> getAllIdentities() {
        var identities = userIdentityRepository.findAll();
        return mapUserIdentitiListToGetDTOList(identities);
    }

    public List<UserIdentityGetDTO> getAllIdentitiesBySerchTerm(String searchTerm) {
        var identities = userIdentityRepository.findByEmailContaining(searchTerm);
        return mapUserIdentitiListToGetDTOList(identities);
    }

    public UserIdentity findByEmail(String email) {
        return userIdentityRepository.findByEmail(email);
    }

    @Transactional
    public UserIdentity createUser(AuthIdentityRequestDTO dto) {
        var identityBuilder = UserIdentity
                .builder()
                .email(dto.getId())
                .password(dto.getId())
                .isGuestAccount(true)
                .build();
        var identity = userIdentityRepository.save(identityBuilder);

        var initialPageIndex = random.nextInt(drinkService.getPagesCount(DrinkPageConfig.PAGE_SIZE));
        var profileBuilder = Profile
                .builder()
                .identity(identity)
                .currentPageIndex(initialPageIndex)
                .initialPageIndex(initialPageIndex)
                .build();
        var profile = profileRepository.save(profileBuilder);

        identity.setProfile(profile);

        return identity;
    }

    public AuthIdentityResponseDTO createNewIdentityDTO(UserIdentity identity) {
        var userDetails = UserIdentityDetails
                .builder()
                .identity(identity)
                .build();
        var token = jwtService.generateToken(new HashMap<>(), userDetails);
        return AuthIdentityResponseDTO
                .builder()
                .identity(mapUserIdentityToGetDTO(identity))
                .token(token)
                .build();
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
                .isGuestAccount(identity.getIsGuestAccount())
                .build();
    }
}
