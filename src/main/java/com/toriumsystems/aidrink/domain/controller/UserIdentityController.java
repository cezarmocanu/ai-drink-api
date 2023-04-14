package com.toriumsystems.aidrink.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.domain.dto.UserIdentityGetDTO;
import com.toriumsystems.aidrink.domain.service.UserIdentityService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

//TODO create a domain wrapper/dto for identity so it is isolated from userIdentity
@RestController
@RequestMapping("/v1/identities")
@AllArgsConstructor
@Validated
public class UserIdentityController {

    private UserIdentityService userIdentityService;

    @GetMapping
    public ResponseEntity<List<UserIdentityGetDTO>> getUserIdentityByQueryString(
            @RequestParam(required = false) @Valid @Size(min = 3, message = "Search term must have at least 3 characters") String searchTerm) {
        if (searchTerm != null) {
            return ResponseEntity.ok(userIdentityService.getAllIdentitiesBySerchTerm(searchTerm));
        }

        return ResponseEntity.ok(userIdentityService.getAllIdentities());
    }
}
