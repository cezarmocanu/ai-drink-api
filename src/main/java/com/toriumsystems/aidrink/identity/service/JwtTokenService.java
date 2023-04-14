package com.toriumsystems.aidrink.identity.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.identity.model.JwtToken;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration-time}")
    private Long tokenExpirationTime;

    private final JwtTokenRepository jwtTokenRepository;

    public String generateToken(Map<String, Object> extraClaims, UserIdentityDetails userDetails) {
        var tokenString = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getIdentity().getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (tokenExpirationTime * 1000)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        var jwt = JwtToken
                .builder()
                .token(tokenString)
                .identity(userDetails.getIdentity())
                .isRevoked(false)
                .build();
        jwtTokenRepository.save(jwt);
        return tokenString;
    }

    public void revokeToken(String token) {
        JwtToken jwtToken = jwtTokenRepository.findByTokenAndIsRevokedFalse(token);
        if (jwtToken == null) {
            return;
        }

        jwtToken.setIsRevoked(true);
        jwtTokenRepository.save(jwtToken);
    }

    public boolean isTokenValid(String token, UserIdentityDetails userDetails) {
        final Long identityId = extractIdentityId(token);
        return (identityId.equals(userDetails.getIdentity().getId())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Long extractIdentityId(String token) {
        var id = extractClaim(token, Claims::getSubject);
        return Long.parseLong(id);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
