package tn.esprit.user.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.Dto.RegisterResponse;
import tn.esprit.user.Service.Interface.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginDisabled() {
        return ResponseEntity.status(HttpStatus.GONE)
                .body(Map.of(
                        "message", "Backend login is disabled. Authenticate only via Keycloak.",
                        "issuer", "http://localhost:8080/realms/food"
                ));
    }
}
