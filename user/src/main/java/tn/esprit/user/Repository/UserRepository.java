package tn.esprit.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.user.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByKeycloakUserId(String keycloakUserId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByKeycloakUserId(String keycloakUserId);
}