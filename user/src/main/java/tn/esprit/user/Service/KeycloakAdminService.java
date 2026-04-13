package tn.esprit.user.Service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import tn.esprit.user.Conf.KeycloakAdminProperties;
import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.exception.ExternalServiceException;
import tn.esprit.user.exception.InvalidRoleException;
import tn.esprit.user.exception.ResourceConflictException;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final KeycloakAdminProperties properties;
    private final Keycloak keycloakAdminClient;

    public String createUser(RegisterRequest request, String assignedRole) {

        try {
            RealmResource realmResource = keycloakAdminClient.realm(properties.getRealm());
            UsersResource usersResource = realmResource.users();

            ensureNoDuplicateUser(usersResource, request.getUsername(), request.getEmail());

            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setEmailVerified(true);
            user.setFirstName(request.getFullName());

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(request.getPassword());
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            Response response = usersResource.create(user);
            int status = response.getStatus();

            if (status == Response.Status.CREATED.getStatusCode()) {
                String keycloakUserId = extractUserId(response.getLocation());
                assignRealmRoleInternal(realmResource, keycloakUserId, assignedRole);
                return keycloakUserId;
            }

            if (status == Response.Status.CONFLICT.getStatusCode()) {
                throw new ResourceConflictException("Username or email already exists in Keycloak");
            }

            throw new ExternalServiceException("Failed to create user in Keycloak. HTTP status: " + status);
        } catch (ResourceConflictException | InvalidRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalServiceException("Keycloak is unavailable or returned an unexpected error", e);
        }
    }

    public void deleteUser(String keycloakUserId) {
        try {
            keycloakAdminClient.realm(properties.getRealm()).users().delete(keycloakUserId);
        } catch (Exception e) {
            throw new ExternalServiceException("Failed to rollback Keycloak user after database failure", e);
        }
    }

    private void assignRealmRoleInternal(RealmResource realmResource, String keycloakUserId, String roleName) {
        RoleRepresentation roleRepresentation;
        try {
            roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
        } catch (Exception e) {
            throw new InvalidRoleException("Invalid Keycloak role: " + roleName);
        }

        realmResource.users()
                .get(keycloakUserId)
                .roles()
                .realmLevel()
                .add(List.of(roleRepresentation));
    }

    private void ensureNoDuplicateUser(UsersResource usersResource, String username, String email) {
        List<UserRepresentation> byUsername = usersResource.searchByUsername(username, true);
        if (byUsername != null && !byUsername.isEmpty()) {
            throw new ResourceConflictException("Username already exists in Keycloak");
        }

        List<UserRepresentation> byEmail = usersResource.searchByEmail(email, true);
        if (byEmail != null && !byEmail.isEmpty()) {
            throw new ResourceConflictException("Email already exists in Keycloak");
        }
    }

    private String extractUserId(URI location) {
        if (location == null) {
            throw new ExternalServiceException("Keycloak did not return user location");
        }
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
