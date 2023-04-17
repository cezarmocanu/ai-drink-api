package com.toriumsystems.aidrink.domain.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.domain.dto.AuthIdentityResponseDTO;
import com.toriumsystems.aidrink.domain.dto.AuthIdentityRequestDTO;
import com.toriumsystems.aidrink.domain.service.UserIdentityService;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.service.JwtTokenService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

record JwtTokenDTO(String token) {
}

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UserIdentityService userIdentityService;
    private JwtTokenService jwtService;

    @PostMapping("/identity")
    public ResponseEntity<AuthIdentityResponseDTO> signUp(@Valid @RequestBody AuthIdentityRequestDTO dto) {
        // TODO handle constraints exception with error
        var identity = userIdentityService.findByEmail(dto.getId());

        if (identity != null) {
            var responseDto = userIdentityService.createNewIdentityDTO(identity);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

        identity = userIdentityService.createUser(dto);
        var responseDto = userIdentityService.createNewIdentityDTO(identity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(Authentication authentication) {
        var userDetails = (UserIdentityDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(new HashMap<>(), userDetails);
        return ResponseEntity.ok(new JwtTokenDTO(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        jwtService.revokeToken(token);
        return ResponseEntity.ok("Token revoked successfully");
    }

}
