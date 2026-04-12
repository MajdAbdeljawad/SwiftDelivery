package tn.esprit.gestiondelivraison.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondelivraison.Entities.SuiviLivraison;
import tn.esprit.gestiondelivraison.Services.SuiviLivraisonService;

import java.util.List;

@RestController
@RequestMapping("/api/suivis")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SuiviLivraisonController {

    private final SuiviLivraisonService suiviService;

    @PostMapping
    public ResponseEntity<SuiviLivraison> create(@RequestBody SuiviLivraison suivi) {
        return ResponseEntity.ok(suiviService.create(suivi));
    }

    @GetMapping
    public ResponseEntity<List<SuiviLivraison>> getAll() {
        return ResponseEntity.ok(suiviService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviLivraison> getById(@PathVariable Long id) {
        return suiviService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuiviLivraison> update(@PathVariable Long id, @RequestBody SuiviLivraison suivi) {
        suivi.setIdSuivi(id);
        return ResponseEntity.ok(suiviService.update(suivi));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        suiviService.delete(id);
        return ResponseEntity.noContent().build();
    }
}