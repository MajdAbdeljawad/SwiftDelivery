package tn.esprit.user.Service.Interface;

import tn.esprit.user.Dto.AuthResponse;
import tn.esprit.user.Dto.LoginRequest;
import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
