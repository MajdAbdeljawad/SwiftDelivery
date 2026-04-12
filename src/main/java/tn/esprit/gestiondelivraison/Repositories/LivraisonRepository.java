package tn.esprit.gestiondelivraison.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondelivraison.Entities.Livraison;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
}