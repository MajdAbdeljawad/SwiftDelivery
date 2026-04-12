package tn.esprit.gestiondelivraison.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondelivraison.Entities.Vehicule;
import tn.esprit.gestiondelivraison.Repositories.VehiculeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    public Optional<Vehicule> getById(Long id) {
        return vehiculeRepository.findById(id);
    }

    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public void delete(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
