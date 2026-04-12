package tn.esprit.gestiondelivraison.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondelivraison.Entities.ZoneLivraison;
import tn.esprit.gestiondelivraison.Services.ZoneLivraisonService;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ZoneLivraisonController {

    private final ZoneLivraisonService zoneService;

    @PostMapping
    public ResponseEntity<ZoneLivraison> create(@RequestBody ZoneLivraison zone) {
        return ResponseEntity.ok(zoneService.create(zone));
    }

    @GetMapping
    public ResponseEntity<List<ZoneLivraison>> getAll() {
        return ResponseEntity.ok(zoneService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneLivraison> getById(@PathVariable Long id) {
        return zoneService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneLivraison> update(@PathVariable Long id, @RequestBody ZoneLivraison zone) {
        zone.setIdZone(id);
        return ResponseEntity.ok(zoneService.update(zone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
