package com.toriumsystems.aidrink.identity.jwt;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toriumsystems.aidrink.identity.model.JwtAuthenticationToken;
import com.toriumsystems.aidrink.identity.model.JwtToken;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;
import com.toriumsystems.aidrink.identity.repository.JwtTokenRepository;
import com.toriumsystems.aidrink.identity.service.JwtTokenService;
import com.toriumsystems.aidrink.identity.service.UserIdentityDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtService;
    private UserIdentityDetailsService userIdentityDetailsService;
    private JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        final Long identityId = jwtService.extractIdentityId(token);

        if (SecurityContextHolder.getContext().getAuthentication() != null || identityId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserIdentityDetails userIdentityDetails;
        try {
            userIdentityDetails = userIdentityDetailsService.loadUserById(identityId);
        } catch (Exception e) {
            // TODO log identity notfound
            filterChain.doFilter(request, response);
            return;
        }

        // TODO add expiration check
        JwtToken jwtToken = jwtTokenRepository.findByTokenAndIsRevokedFalse(token);

        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtAuthenticationToken authToken = new JwtAuthenticationToken(userIdentityDetails, token, new ArrayList<>());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        authToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}
