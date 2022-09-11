package com.dimas.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "keycloak-admin")
public class KeycloakProps {
    private String authServerUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
}
