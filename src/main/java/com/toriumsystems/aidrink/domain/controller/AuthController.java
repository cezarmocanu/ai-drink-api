package com.toriumsystems.aidrink.domain.controller;

import java.util.HashMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.toriumsystems.aidrink.domain.dto.AuthNewIdentityDTO;
import com.toriumsystems.aidrink.domain.dto.AuthSignupDTO;
import com.toriumsystems.aidrink.domain.service.UserIdentityService;
import com.toriumsystems.aidrink.identity.model.UserIdentity;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.service.JwtTokenService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.ConstraintViolationException;
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

    @PostMapping("/signup")
    public ResponseEntity<AuthNewIdentityDTO> signUp(@Valid @RequestBody AuthSignupDTO dto) {
        // TODO handle constraints exception with error
        var identity = userIdentityService.createUser(dto);
        var userDetails = UserIdentityDetails
                .builder()
                .identity(identity)
                .build();
        var token = jwtService.generateToken(new HashMap<>(), userDetails);
        var responseDto = AuthNewIdentityDTO
                .builder()
                .identity(userIdentityService.mapUserIdentityToGetDTO(identity))
                .token(token)
                .build();
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
