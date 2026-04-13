package tn.esprit.user.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.user.Dto.UserAdminDto;
import tn.esprit.user.Service.KeycloakUserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final KeycloakUserService keycloakUserService;

    @GetMapping
    public ResponseEntity<List<UserAdminDto>> getAllUsers() {
        return ResponseEntity.ok(keycloakUserService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserAdminDto>> searchUsersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(keycloakUserService.searchUsersByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAdminDto> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(keycloakUserService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserAdminDto dto) {
        keycloakUserService.updateUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        keycloakUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
