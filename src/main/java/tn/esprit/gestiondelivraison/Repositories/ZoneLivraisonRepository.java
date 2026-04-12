package tn.esprit.gestiondelivraison.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondelivraison.Entities.ZoneLivraison;

@Repository
public interface ZoneLivraisonRepository extends JpaRepository<ZoneLivraison, Long> {
}
