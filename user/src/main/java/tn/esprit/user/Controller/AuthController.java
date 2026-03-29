package tn.esprit.user.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.user.Dto.AuthResponse;
import tn.esprit.user.Dto.LoginRequest;
import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.Service.Interface.UserService;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(userService.register(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("REGISTER ERROR: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("LOGIN ERROR: " + e.getMessage());
        }}
}
