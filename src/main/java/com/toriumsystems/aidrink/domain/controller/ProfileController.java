package com.toriumsystems.aidrink.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.domain.service.ProfileService;
import com.toriumsystems.aidrink.identity.annotations.IdentityId;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/votes")
    public ResponseEntity<String> createDrinkVote(@IdentityId Long identityId) {
        profileService.createVote(identityId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
