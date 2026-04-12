package tn.esprit.gestiondelivraison.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondelivraison.Entities.SuiviLivraison;
import tn.esprit.gestiondelivraison.Repositories.SuiviLivraisonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuiviLivraisonService {

    private final SuiviLivraisonRepository suiviRepository;

    public SuiviLivraison create(SuiviLivraison suivi) {
        return suiviRepository.save(suivi);
    }

    public List<SuiviLivraison> getAll() {
        return suiviRepository.findAll();
    }

    public Optional<SuiviLivraison> getById(Long id) {
        return suiviRepository.findById(id);
    }

    public SuiviLivraison update(SuiviLivraison suivi) {
        return suiviRepository.save(suivi);
    }

    public void delete(Long id) {
        suiviRepository.deleteById(id);
    }
}
