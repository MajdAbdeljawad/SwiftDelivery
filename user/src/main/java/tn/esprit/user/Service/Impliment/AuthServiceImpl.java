package tn.esprit.user.Service.Impliment;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.Dto.RegisterResponse;
import tn.esprit.user.Repository.UserRepository;
import tn.esprit.user.Service.Interface.AuthService;
import tn.esprit.user.Service.KeycloakAdminService;
import tn.esprit.user.entity.User;
import tn.esprit.user.exception.ExternalServiceException;
import tn.esprit.user.exception.ResourceConflictException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_LIVREUR = "LIVREUR";
    private static final String ROLE_ADMIN = "ADMIN";

    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;

    @Value("${keycloak.registration.allow-public-livreur:false}")
    private boolean allowPublicLivreur;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        validatePasswords(request);

        String assignedRole = resolvePublicRole(request.getRole());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceConflictException("Username already exists in profile database");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceConflictException("Email already exists in profile database");
        }

        String keycloakUserId = keycloakAdminService.createUser(request, assignedRole);

        try {
            User user = User.builder()
                    .keycloakUserId(keycloakUserId)
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .role(assignedRole)
                    .build();

            User savedUser = userRepository.save(user);

            return new RegisterResponse(
                    "User registered successfully",
                    savedUser.getId(),
                    savedUser.getKeycloakUserId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.getFullName(),
                    savedUser.getRole()
            );
        } catch (DataIntegrityViolationException e) {
            rollbackKeycloakUser(keycloakUserId);
            throw new ResourceConflictException("Profile already exists in database", e);
        } catch (Exception e) {
            rollbackKeycloakUser(keycloakUserId);
            throw new ExternalServiceException("Registration failed while saving profile data", e);
        }
    }

    private void validatePasswords(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirmPassword do not match");
        }
    }

    private String resolvePublicRole(String requestedRole) {
        if (requestedRole == null || requestedRole.isBlank()) {
            return ROLE_USER;
        }

        String normalizedRole = requestedRole.trim().toUpperCase();

        if (ROLE_ADMIN.equals(normalizedRole)) {
            throw new IllegalArgumentException("Public registration cannot assign ADMIN role");
        }

        if (ROLE_LIVREUR.equals(normalizedRole)) {
            if (allowPublicLivreur) {
                return ROLE_LIVREUR;
            }
            return ROLE_USER;
        }

        return ROLE_USER;
    }

    private void rollbackKeycloakUser(String keycloakUserId) {
        try {
            keycloakAdminService.deleteUser(keycloakUserId);
        } catch (Exception ignored) {
            // Best-effort compensation to avoid dangling Keycloak users when DB save fails.
        }
    }
}
