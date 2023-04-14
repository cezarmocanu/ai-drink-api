package com.toriumsystems.aidrink.identity.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.service.JwtTokenService;

import lombok.AllArgsConstructor;

record JwtTokenDTO(String token) {
}

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {

    private JwtTokenService jwtService;

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
        return ResponseEntity.ok("");
    }

}
