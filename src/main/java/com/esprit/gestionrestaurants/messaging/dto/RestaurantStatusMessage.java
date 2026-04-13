package com.esprit.gestionrestaurants.messaging.dto;

public class RestaurantStatusMessage {

    private Long restaurantId;
    private String nom;
    private String statut;
    private boolean disponible;

    public RestaurantStatusMessage() {
    }

    public RestaurantStatusMessage(Long restaurantId, String nom, String statut, boolean disponible) {
        this.restaurantId = restaurantId;
        this.nom = nom;
        this.statut = statut;
        this.disponible = disponible;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
