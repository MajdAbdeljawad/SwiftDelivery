package tn.esprit.user.Conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakAdminProperties {
    private String serverUrl;
    private String realm;
    private String adminRealm = "master";
    private String adminClientId = "admin-cli";
    private String adminUsername;
    private String adminPassword;
}
