package com.esprit.gestionrestaurants.service.impl;

import com.esprit.gestionrestaurants.client.LivraisonServiceClient;
import com.esprit.gestionrestaurants.client.dto.ZoneLivraisonDto;
import com.esprit.gestionrestaurants.entity.Adresse;
import com.esprit.gestionrestaurants.entity.Horaire;
import com.esprit.gestionrestaurants.entity.Restaurant;
import com.esprit.gestionrestaurants.entity.ZoneLivraison;
import com.esprit.gestionrestaurants.enums.CategorieRestaurant;
import com.esprit.gestionrestaurants.enums.StatutRestaurant;
import com.esprit.gestionrestaurants.exception.ResourceNotFoundException;
import com.esprit.gestionrestaurants.messaging.RestaurantStatusProducer;
import com.esprit.gestionrestaurants.messaging.dto.RestaurantStatusMessage;
import com.esprit.gestionrestaurants.repository.RestaurantRepository;
import com.esprit.gestionrestaurants.service.RestaurantService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final LivraisonServiceClient livraisonServiceClient;
    private final RestaurantStatusProducer restaurantStatusProducer;

    public RestaurantServiceImpl(
            RestaurantRepository restaurantRepository,
            LivraisonServiceClient livraisonServiceClient,
            RestaurantStatusProducer restaurantStatusProducer) {
        this.restaurantRepository = restaurantRepository;
        this.livraisonServiceClient = livraisonServiceClient;
        this.restaurantStatusProducer = restaurantStatusProducer;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Le restaurant à créer est obligatoire.");
        }
        validateForCreate(restaurant);
        if (restaurant.getHoraires() != null) {
            for (Horaire h : restaurant.getHoraires()) {
                h.setRestaurant(restaurant);
            }
        }
        if (restaurant.getZonesLivraison() != null) {
            for (ZoneLivraison z : restaurant.getZonesLivraison()) {
                z.setRestaurant(restaurant);
            }
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(Long id) {
        requireRestaurantId(id);
        Restaurant restaurant = restaurantRepository
                .findWithHorairesAndAdresseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
        Hibernate.initialize(restaurant.getZonesLivraison());
        return restaurant;
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        requireRestaurantId(id);
        if (restaurant == null) {
            throw new IllegalArgumentException("Le corps de la mise à jour est obligatoire.");
        }
        Restaurant existing = getRestaurantById(id);
        existing.setNom(restaurant.getNom());
        existing.setDescription(restaurant.getDescription());
        existing.setTelephone(restaurant.getTelephone());
        existing.setEmail(restaurant.getEmail());
        existing.setCategorie(restaurant.getCategorie());
        existing.setStatut(restaurant.getStatut());
        existing.setDisponible(restaurant.getDisponible());
        existing.setFraisLivraison(restaurant.getFraisLivraison());
        existing.setTempsPreparationMoyen(restaurant.getTempsPreparationMoyen());
        if (restaurant.getAdresse() != null && existing.getAdresse() != null) {
            mergeAdresse(existing.getAdresse(), restaurant.getAdresse());
        }
        return restaurantRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        requireRestaurantId(id);
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant", id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> searchByNom(String nom) {
        if (nom == null || nom.isBlank()) {
            return restaurantRepository.findAll();
        }
        return restaurantRepository.searchByNom(nom.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> getByCategorie(CategorieRestaurant categorie) {
        if (categorie == null) {
            throw new IllegalArgumentException("La catégorie est obligatoire.");
        }
        return restaurantRepository.findByCategorie(categorie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurantsDisponibles() {
        return restaurantRepository.findByDisponibleTrue();
    }

    @Override
    @Transactional
    public Restaurant changeStatut(Long id, StatutRestaurant statut) {
        requireRestaurantId(id);
        if (statut == null) {
            throw new IllegalArgumentException("Le statut est obligatoire.");
        }
        Restaurant restaurant = loadAggregateForBusinessRules(id);
        restaurant.setStatut(statut);
        Restaurant saved = restaurantRepository.save(restaurant);
        restaurantStatusProducer.send(
                new RestaurantStatusMessage(
                        saved.getId(),
                        saved.getNom(),
                        saved.getStatut().name(),
                        Boolean.TRUE.equals(saved.getDisponible())));
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estOuvertMaintenant(Long id) {
        return loadAggregateForBusinessRules(id).estOuvertMaintenant();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estCommandable(Long id) {
        return loadAggregateForBusinessRules(id).estCommandable();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estLivrable(Long id, Double latitudeClient, Double longitudeClient) {
        validateCoordonneesClient(latitudeClient, longitudeClient);
        return loadAggregateForBusinessRules(id).estLivrablePourClient(latitudeClient, longitudeClient);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estCommandablePourClient(Long id, Double latitudeClient, Double longitudeClient) {
        validateCoordonneesClient(latitudeClient, longitudeClient);
        return loadAggregateForBusinessRules(id).estCommandablePourClient(latitudeClient, longitudeClient);
    }

    @Override
    public List<ZoneLivraisonDto> listerZonesLivraison() {
        return livraisonServiceClient.getAllZones();
    }

    /**
     * Restaurant avec horaires et adresse initialisés (prérequis aux règles métier sur les créneaux).
     */
    private Restaurant loadAggregateForBusinessRules(Long id) {
        requireRestaurantId(id);
        return restaurantRepository
                .findWithHorairesAndAdresseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
    }

    private static void requireRestaurantId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant du restaurant est obligatoire.");
        }
    }

    private static void validateCoordonneesClient(Double latitudeClient, Double longitudeClient) {
        if (latitudeClient == null || longitudeClient == null) {
            throw new IllegalArgumentException("Latitude et longitude client sont obligatoires.");
        }
        if (latitudeClient < -90 || latitudeClient > 90) {
            throw new IllegalArgumentException("Latitude client invalide.");
        }
        if (longitudeClient < -180 || longitudeClient > 180) {
            throw new IllegalArgumentException("Longitude client invalide.");
        }
    }

    private void mergeAdresse(Adresse target, Adresse source) {
        target.setRue(source.getRue());
        target.setVille(source.getVille());
        target.setCodePostal(source.getCodePostal());
        target.setLatitude(source.getLatitude());
        target.setLongitude(source.getLongitude());
    }

    private static void validateForCreate(Restaurant r) {
        if (r.getNom() == null || r.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du restaurant est obligatoire.");
        }
        if (r.getAdresse() == null) {
            throw new IllegalArgumentException("L'adresse est obligatoire.");
        }
        Adresse a = r.getAdresse();
        if (a.getRue() == null || a.getRue().isBlank()
                || a.getVille() == null || a.getVille().isBlank()
                || a.getCodePostal() == null || a.getCodePostal().isBlank()) {
            throw new IllegalArgumentException("L'adresse doit contenir rue, ville et code postal.");
        }
        if (r.getCategorie() == null) {
            throw new IllegalArgumentException("La catégorie est obligatoire.");
        }
        if (r.getStatut() == null) {
            throw new IllegalArgumentException("Le statut est obligatoire.");
        }
        if (r.getDisponible() == null) {
            throw new IllegalArgumentException("Le champ disponible est obligatoire.");
        }
        if (r.getFraisLivraison() == null) {
            throw new IllegalArgumentException("Les frais de livraison sont obligatoires.");
        }
        if (r.getTempsPreparationMoyen() == null) {
            throw new IllegalArgumentException("Le temps de préparation moyen est obligatoire.");
        }
        if (r.getZonesLivraison() != null) {
            for (ZoneLivraison zone : r.getZonesLivraison()) {
                if (zone.getRayonKm() == null || zone.getRayonKm() <= 0) {
                    throw new IllegalArgumentException("Chaque zone de livraison doit avoir un rayonKm > 0.");
                }
            }
        }
    }
}
