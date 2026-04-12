package com.esprit.gestionrestaurants.repository;

import com.esprit.gestionrestaurants.entity.Restaurant;
import com.esprit.gestionrestaurants.enums.CategorieRestaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * CRUD standard via {@link JpaRepository} ({@code save}, {@code findById}, {@code findAll}, {@code deleteById}, …).
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Restaurant> searchByNom(@Param("nom") String nom);

    List<Restaurant> findByCategorie(CategorieRestaurant categorie);

    List<Restaurant> findByDisponibleTrue();

    /**
     * Horaires + adresse chargés pour {@code estOuvertMaintenant}, {@code estCommandable} et cohérence du détail.
     */
    @EntityGraph(attributePaths = {"horaires", "adresse"})
    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> findWithHorairesAndAdresseById(@Param("id") Long id);
}
