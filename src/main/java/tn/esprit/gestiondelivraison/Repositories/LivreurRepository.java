package tn.esprit.gestiondelivraison.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestiondelivraison.Entities.Livreur;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long> {
}
