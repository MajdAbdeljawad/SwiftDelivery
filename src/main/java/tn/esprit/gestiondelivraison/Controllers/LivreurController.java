package tn.esprit.gestiondelivraison.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondelivraison.Entities.Livreur;
import tn.esprit.gestiondelivraison.Services.LivreurService;

import java.util.List;

@RestController
@RequestMapping("/api/livreurs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LivreurController {

    private final LivreurService livreurService;

    @PostMapping
    public ResponseEntity<Livreur> create(@RequestBody Livreur livreur) {
        return ResponseEntity.ok(livreurService.create(livreur));
    }

    @GetMapping
    public ResponseEntity<List<Livreur>> getAll() {
        return ResponseEntity.ok(livreurService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getById(@PathVariable Long id) {
        return livreurService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livreur> update(@PathVariable Long id, @RequestBody Livreur livreur) {
        livreur.setIdLivreur(id);
        return ResponseEntity.ok(livreurService.update(livreur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livreurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
