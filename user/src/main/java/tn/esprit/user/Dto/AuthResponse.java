package tn.esprit.user.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private Long id;
    private String fullName;
    private String email;
    private String role;
}
