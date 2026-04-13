package tn.esprit.user.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminDto {
    private String id;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "email format is invalid")
    private String email;

    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String phone;
    private String address;
}
