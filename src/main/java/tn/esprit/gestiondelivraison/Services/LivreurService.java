package tn.esprit.gestiondelivraison.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondelivraison.Entities.Livreur;
import tn.esprit.gestiondelivraison.Repositories.LivreurRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivreurService {

    private final LivreurRepository livreurRepository;

    public Livreur create(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    public List<Livreur> getAll() {
        return livreurRepository.findAll();
    }

    public Optional<Livreur> getById(Long id) {
        return livreurRepository.findById(id);
    }

    public Livreur update(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    public void delete(Long id) {
        livreurRepository.deleteById(id);
    }
}