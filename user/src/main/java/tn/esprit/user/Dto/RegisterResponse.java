package tn.esprit.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private Long id;
    private String keycloakUserId;
    private String username;
    private String email;
    private String fullName;
    private String role;
}
