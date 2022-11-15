package com.dimas.profile.config;

import com.dimas.profile.config.prop.KeycloakProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProps keycloakProps;

    @Bean
    public RealmResource keycloak() {
        log.debug("KeycloakProps={}", keycloakProps);
        final var keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakProps.getAuthServerUrl())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakProps.getClientId())
                .clientSecret(keycloakProps.getClientSecret())
                .realm(keycloakProps.getRealm())
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                )
                .build();
            log.debug("Requesting admin access token");
            final var token = keycloak.tokenManager().getAccessToken();
            log.debug("AccessTokenResponse: TokenType={}, Error={}, ErrorDescription={}", token.getTokenType(), token.getError(), token.getErrorDescription());
            return keycloak.realm(keycloakProps.getRealm());
    }
}
