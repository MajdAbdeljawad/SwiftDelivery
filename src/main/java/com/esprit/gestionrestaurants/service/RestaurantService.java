package com.esprit.gestionrestaurants.service;

import com.esprit.gestionrestaurants.client.dto.ZoneLivraisonDto;
import com.esprit.gestionrestaurants.entity.Restaurant;
import com.esprit.gestionrestaurants.enums.CategorieRestaurant;
import com.esprit.gestionrestaurants.enums.StatutRestaurant;
import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long id);

    Restaurant updateRestaurant(Long id, Restaurant restaurant);

    void deleteRestaurant(Long id);

    List<Restaurant> searchByNom(String nom);

    List<Restaurant> getByCategorie(CategorieRestaurant categorie);

    /** Restaurants marqués disponibles ({@code disponible == true}). */
    List<Restaurant> getRestaurantsDisponibles();

    Restaurant changeStatut(Long id, StatutRestaurant statut);

    boolean estOuvertMaintenant(Long id);

    boolean estCommandable(Long id);

    boolean estLivrable(Long id, Double latitudeClient, Double longitudeClient);

    boolean estCommandablePourClient(Long id, Double latitudeClient, Double longitudeClient);

    List<ZoneLivraisonDto> listerZonesLivraison();
}
