package com.toriumsystems.aidrink.identity.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private UserIdentityDetails principal;
    private String credentials;

    public JwtAuthenticationToken(
            UserIdentityDetails principal,
            String token,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = token;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
