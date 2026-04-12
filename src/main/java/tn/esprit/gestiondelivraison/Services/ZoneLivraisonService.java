package tn.esprit.gestiondelivraison.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondelivraison.Entities.ZoneLivraison;
import tn.esprit.gestiondelivraison.Repositories.ZoneLivraisonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneLivraisonService {

    private final ZoneLivraisonRepository zoneRepository;

    public ZoneLivraison create(ZoneLivraison zone) {
        return zoneRepository.save(zone);
    }

    public List<ZoneLivraison> getAll() {
        return zoneRepository.findAll();
    }

    public Optional<ZoneLivraison> getById(Long id) {
        return zoneRepository.findById(id);
    }

    public ZoneLivraison update(ZoneLivraison zone) {
        return zoneRepository.save(zone);
    }

    public void delete(Long id) {
        zoneRepository.deleteById(id);
    }
}