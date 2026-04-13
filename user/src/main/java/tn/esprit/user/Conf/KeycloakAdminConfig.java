package tn.esprit.user.Conf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminConfig {

    private final KeycloakAdminProperties properties;

    @Bean
    public Keycloak keycloakAdminClient() {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(properties.getServerUrl())
                    .realm(properties.getAdminRealm())
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(properties.getAdminClientId())
                    .username(properties.getAdminUsername())
                    .password(properties.getAdminPassword())
                    .build();
            log.info("✅ Keycloak Admin Client initialized successfully");
            return keycloak;
        } catch (Exception e) {
            log.warn("⚠️ Failed to initialize Keycloak Admin Client: {}. Service will still work but Keycloak admin functions may be unavailable.", e.getMessage());
            return null;
        }
    }
}
