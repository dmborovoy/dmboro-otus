package com.dimas.security;

import com.dimas.utils.Constants;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

import java.util.Collection;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
public class CustomJwtAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> {
    private final String name;

    public CustomJwtAuthenticationToken(Jwt token, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(token, principal, token.getTokenValue(), authorities);
        setAuthenticated(true);
        name = token.getSubject();
    }

    public Map<String, Object> getTokenAttributes() {
        return getToken().getClaims();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getClient() {
        return (String) getToken().getClaims().get(Constants.JWT_CLIENT_ID_CLAIM);
    }
}
