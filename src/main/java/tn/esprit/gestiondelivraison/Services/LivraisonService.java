package tn.esprit.gestiondelivraison.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondelivraison.Entities.Livraison;
import tn.esprit.gestiondelivraison.Entities.StatutLivraison;
import tn.esprit.gestiondelivraison.Repositories.LivraisonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;

    public Livraison create(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    public Livraison changerStatut(Long id, StatutLivraison nouveauStatut) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée !"));

        livraison.setStatut(nouveauStatut);
        livraisonRepository.save(livraison);

        System.out.println("Notification : Livraison " + livraison.getIdLivraison() +
                " est maintenant " + nouveauStatut);

        return livraison;
    }

    public List<Livraison> getAll() {
        return livraisonRepository.findAll();
    }

    public Optional<Livraison> getById(Long id) {
        return livraisonRepository.findById(id);
    }

    public Livraison update(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    public void delete(Long id) {
        livraisonRepository.deleteById(id);
    }
}