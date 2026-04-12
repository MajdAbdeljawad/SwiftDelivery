package tn.esprit.gestiondelivraison.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondelivraison.Entities.SuiviLivraison;

@Repository
public interface SuiviLivraisonRepository extends JpaRepository<SuiviLivraison, Long> {
}