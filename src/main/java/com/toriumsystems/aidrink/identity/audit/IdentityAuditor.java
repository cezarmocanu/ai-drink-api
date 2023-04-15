package com.toriumsystems.aidrink.identity.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.toriumsystems.aidrink.identity.model.JwtAuthenticationToken;
import com.toriumsystems.aidrink.identity.model.UserIdentityDetails;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class IdentityAuditor implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.of(0L);
        }

        var authentication = (JwtAuthenticationToken) auth;
        var identityId = ((UserIdentityDetails) authentication.getPrincipal()).getIdentity().getId();
        return Optional.of(identityId);
    }
}
