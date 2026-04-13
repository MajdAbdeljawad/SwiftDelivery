package tn.esprit.user.Service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.esprit.user.Conf.KeycloakAdminProperties;
import tn.esprit.user.Dto.UserAdminDto;
import tn.esprit.user.exception.ExternalServiceException;
import tn.esprit.user.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private static final String ATTR_PHONE = "phone";
    private static final String ATTR_ADDRESS = "address";

    private final Keycloak keycloakAdminClient;
    private final KeycloakAdminProperties properties;

    public List<UserAdminDto> getAllUsers() {
        if (keycloakAdminClient == null) {
            throw new ExternalServiceException("Keycloak Admin Client is not initialized. Ensure Keycloak is running and credentials are correct.");
        }
        try {
            List<UserRepresentation> users = usersResource().list();
            List<UserAdminDto> result = new ArrayList<>();
            for (UserRepresentation user : users) {
                result.add(toDto(user));
            }
            return result;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to fetch users from Keycloak", ex);
        }
    }

    public List<UserAdminDto> searchUsersByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("email is required");
        }

        try {
            String normalized = email.trim().toLowerCase(Locale.ROOT);
            List<UserRepresentation> matches = usersResource().searchByEmail(email.trim(), true);
            if (matches == null) {
                matches = List.of();
            }

            List<UserAdminDto> result = new ArrayList<>();
            for (UserRepresentation user : matches) {
                String userEmail = user.getEmail();
                if (userEmail != null && userEmail.trim().toLowerCase(Locale.ROOT).equals(normalized)) {
                    result.add(toDto(user));
                }
            }
            return result;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to search users by email in Keycloak", ex);
        }
    }

    public UserAdminDto getUserById(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("userId is required");
        }

        try {
            UserRepresentation user = usersResource().get(userId).toRepresentation();
            if (user == null) {
                throw new ResourceNotFoundException("User not found: " + userId);
            }
            return toDto(user);
        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User not found: " + userId);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to get user details from Keycloak", ex);
        }
    }

    public void updateUser(String userId, UserAdminDto dto) {
        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("userId is required");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        try {
            UserRepresentation existing = usersResource().get(userId).toRepresentation();
            if (existing == null) {
                throw new ResourceNotFoundException("User not found: " + userId);
            }

            existing.setUsername(dto.getUsername());
            existing.setEmail(dto.getEmail());
            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            if (dto.getEnabled() != null) {
                existing.setEnabled(dto.getEnabled());
            }

            Map<String, List<String>> attributes = existing.getAttributes();
            if (attributes == null) {
                attributes = new HashMap<>();
            }

            putSingleAttribute(attributes, ATTR_PHONE, dto.getPhone());
            putSingleAttribute(attributes, ATTR_ADDRESS, dto.getAddress());
            existing.setAttributes(attributes);

            usersResource().get(userId).update(existing);
        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User not found: " + userId);
        } catch (ResourceNotFoundException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to update user in Keycloak", ex);
        }
    }

    public void deleteUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("userId is required");
        }

        try {
            usersResource().get(userId).toRepresentation();
            usersResource().delete(userId);
        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException("User not found: " + userId);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to delete user in Keycloak", ex);
        }
    }

    private UsersResource usersResource() {
        RealmResource realmResource = keycloakAdminClient.realm(properties.getRealm());
        return realmResource.users();
    }

    private UserAdminDto toDto(UserRepresentation user) {
        Map<String, List<String>> attrs = user.getAttributes();

        return UserAdminDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(Boolean.TRUE.equals(user.isEnabled()))
                .phone(readFirstAttribute(attrs, ATTR_PHONE))
                .address(readFirstAttribute(attrs, ATTR_ADDRESS))
                .build();
    }

    private String readFirstAttribute(Map<String, List<String>> attributes, String key) {
        if (attributes == null) {
            return null;
        }

        List<String> values = attributes.get(key);
        if (values == null || values.isEmpty()) {
            return null;
        }

        return values.get(0);
    }

    private void putSingleAttribute(Map<String, List<String>> attributes, String key, String value) {
        if (StringUtils.hasText(value)) {
            attributes.put(key, List.of(value.trim()));
        } else {
            attributes.remove(key);
        }
    }
}
