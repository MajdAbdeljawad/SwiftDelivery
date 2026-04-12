package com.esprit.gestionrestaurants.controller;

import com.esprit.gestionrestaurants.client.dto.ZoneLivraisonDto;
import com.esprit.gestionrestaurants.messaging.RestaurantStatusProducer;
import com.esprit.gestionrestaurants.messaging.dto.RestaurantStatusMessage;
import com.esprit.gestionrestaurants.entity.Restaurant;
import com.esprit.gestionrestaurants.enums.CategorieRestaurant;
import com.esprit.gestionrestaurants.enums.StatutRestaurant;
import com.esprit.gestionrestaurants.service.RestaurantService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantStatusProducer restaurantStatusProducer;

    public RestaurantController(RestaurantService restaurantService, RestaurantStatusProducer restaurantStatusProducer) {
        this.restaurantService = restaurantService;
        this.restaurantStatusProducer = restaurantStatusProducer;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.createRestaurant(restaurant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Restaurant>> getRestaurantsDisponibles() {
        return ResponseEntity.ok(restaurantService.getRestaurantsDisponibles());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchByNom(@RequestParam(required = false) String nom) {
        return ResponseEntity.ok(restaurantService.searchByNom(nom));
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Restaurant>> getByCategorie(@PathVariable CategorieRestaurant categorie) {
        return ResponseEntity.ok(restaurantService.getByCategorie(categorie));
    }

    @GetMapping("/test/zones-livraison")
    public ResponseEntity<List<ZoneLivraisonDto>> testFeignZones() {
        return ResponseEntity.ok(restaurantService.listerZonesLivraison());
    }

    @PostMapping("/test/send-status")
    public ResponseEntity<Void> testSendStatus(@RequestBody(required = false) RestaurantStatusMessage body) {
        RestaurantStatusMessage message =
                body != null
                        ? body
                        : new RestaurantStatusMessage(1L, "Restaurant démo", "ACTIF", true);
        restaurantStatusProducer.send(message);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}/ouvert")
    public ResponseEntity<Map<String, Boolean>> getOuvert(@PathVariable Long id) {
        boolean ouvert = restaurantService.estOuvertMaintenant(id);
        return ResponseEntity.ok(Map.of("ouvert", ouvert));
    }

    @GetMapping("/{id}/commandable")
    public ResponseEntity<Map<String, Boolean>> getCommandable(@PathVariable Long id) {
        boolean commandable = restaurantService.estCommandable(id);
        return ResponseEntity.ok(Map.of("commandable", commandable));
    }

    @GetMapping("/{id}/livrable")
    public ResponseEntity<Map<String, Boolean>> getLivrable(
            @PathVariable Long id,
            @RequestParam(name = "latitude") Double latitudeClient,
            @RequestParam(name = "longitude") Double longitudeClient) {
        boolean livrable = restaurantService.estLivrable(id, latitudeClient, longitudeClient);
        return ResponseEntity.ok(Map.of("livrable", livrable));
    }

    @GetMapping("/{id}/commandable-client")
    public ResponseEntity<Map<String, Boolean>> getCommandablePourClient(
            @PathVariable Long id,
            @RequestParam(name = "latitude") Double latitudeClient,
            @RequestParam(name = "longitude") Double longitudeClient) {
        boolean commandable =
                restaurantService.estCommandablePourClient(id, latitudeClient, longitudeClient);
        return ResponseEntity.ok(Map.of("commandable", commandable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id, @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<Restaurant> changeStatut(
            @PathVariable Long id, @RequestParam(name = "statut") StatutRestaurant statut) {
        return ResponseEntity.ok(restaurantService.changeStatut(id, statut));
    }
}
