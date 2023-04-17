package com.toriumsystems.aidrink.domain.service;

import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.domain.model.Profile;
import com.toriumsystems.aidrink.domain.repository.ProfileRepository;
import com.toriumsystems.aidrink.domain.types.DrinkPageConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile findProfileByIdentityId(Long identityId) {
        return profileRepository.findByIdentityId(identityId);
    }

    public void createVote(Long identityId) {
        var profile = findProfileByIdentityId(identityId);
        profile.setCurrentPageOffset((profile.getCurrentPageOffset() + 1) % DrinkPageConfig.PAGE_SIZE);
        profileRepository.save(profile);
    }
}
