package tn.esprit.gestiondelivraison.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondelivraison.Entities.Livraison;
import tn.esprit.gestiondelivraison.Entities.StatutLivraison;
import tn.esprit.gestiondelivraison.Services.LivraisonService;

import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LivraisonController {

    private final LivraisonService livraisonService;

    @PostMapping
    public ResponseEntity<Livraison> create(@RequestBody Livraison livraison) {
        return ResponseEntity.ok(livraisonService.create(livraison));
    }

    @GetMapping
    public ResponseEntity<List<Livraison>> getAll() {
        return ResponseEntity.ok(livraisonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getById(@PathVariable Long id) {
        return livraisonService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> update(@PathVariable Long id, @RequestBody Livraison livraison) {
        livraison.setIdLivraison(id);
        return ResponseEntity.ok(livraisonService.update(livraison));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livraisonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Livraison> changerStatut(@PathVariable Long id,
                                                   @RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.changerStatut(id, statut));
    }
}