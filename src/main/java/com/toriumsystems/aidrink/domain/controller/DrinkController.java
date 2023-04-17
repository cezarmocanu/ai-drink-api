package com.toriumsystems.aidrink.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.domain.dto.DrinkGetDTO;
import com.toriumsystems.aidrink.domain.repository.ProfileRepository;
import com.toriumsystems.aidrink.domain.service.DrinkService;
import com.toriumsystems.aidrink.identity.annotations.IdentityId;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/drinks")
public class DrinkController {

    private final DrinkService drinkService;
    private final ProfileRepository profileRepository;

    @GetMapping
    public ResponseEntity<List<DrinkGetDTO>> getAllDrinks(@IdentityId Long identityId) {
        var profile = profileRepository.findByIdentityId(identityId);
        var pageIndex = profile.getCurrentPageIndex() != null ? profile.getCurrentPageIndex() : 0;
        return ResponseEntity.ok(drinkService.getShuffledDrinksByPage(pageIndex, 10));
    }
}
