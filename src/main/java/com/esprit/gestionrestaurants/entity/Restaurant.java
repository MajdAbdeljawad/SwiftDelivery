package com.esprit.gestionrestaurants.entity;

import com.esprit.gestionrestaurants.enums.CategorieRestaurant;
import com.esprit.gestionrestaurants.enums.JourSemaine;
import com.esprit.gestionrestaurants.enums.StatutRestaurant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.BatchSize;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nom;

    @Column(length = 2000)
    private String description;

    @Column(length = 30)
    private String telephone;

    @Column(length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategorieRestaurant categorie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatutRestaurant statut;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(name = "frais_livraison", nullable = false)
    private Double fraisLivraison;

    @Column(name = "temps_preparation_moyen", nullable = false)
    private Integer tempsPreparationMoyen;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "adresse_id", nullable = false, unique = true)
    private Adresse adresse;

    @BatchSize(size = 32)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Horaire> horaires = new ArrayList<>();

    @BatchSize(size = 32)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ZoneLivraison> zonesLivraison = new ArrayList<>();

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CategorieRestaurant getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieRestaurant categorie) {
        this.categorie = categorie;
    }

    public StatutRestaurant getStatut() {
        return statut;
    }

    public void setStatut(StatutRestaurant statut) {
        this.statut = statut;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public Integer getTempsPreparationMoyen() {
        return tempsPreparationMoyen;
    }

    public void setTempsPreparationMoyen(Integer tempsPreparationMoyen) {
        this.tempsPreparationMoyen = tempsPreparationMoyen;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Horaire> getHoraires() {
        return horaires;
    }

    public void setHoraires(List<Horaire> horaires) {
        this.horaires = horaires;
    }

    public List<ZoneLivraison> getZonesLivraison() {
        return zonesLivraison;
    }

    public void setZonesLivraison(List<ZoneLivraison> zonesLivraison) {
        this.zonesLivraison = zonesLivraison;
    }

    public void activer() {
        this.statut = StatutRestaurant.ACTIF;
    }

    public void desactiver() {
        this.statut = StatutRestaurant.INACTIF;
    }

    public void suspendre() {
        this.statut = StatutRestaurant.SUSPENDU;
    }

    /** Opération du diagramme sans paramètre ; surcharge {@link #mettreAJourInfos(String, String, String, String)} pour appliquer les valeurs. */
    public void mettreAJourInfos() {
        // Point d'extension métier (validation, événements) ; les champs passent par la surcharge ou les setters.
    }

    /**
     * Mise à jour des informations de présentation. Les paramètres {@code null} sont ignorés.
     */
    public void mettreAJourInfos(String nom, String description, String telephone, String email) {
        if (nom != null) {
            this.nom = nom;
        }
        if (description != null) {
            this.description = description;
        }
        if (telephone != null) {
            this.telephone = telephone;
        }
        if (email != null) {
            this.email = email;
        }
    }

    /**
     * Indique si le restaurant a au moins un créneau d'ouverture qui couvre l'heure actuelle
     * (calcul dynamique, cf. diagramme).
     */
    public boolean estOuvertMaintenant() {
        if (horaires == null || horaires.isEmpty()) {
            return false;
        }
        JourSemaine aujourdhui = jourDepuisDayOfWeek(LocalDate.now().getDayOfWeek());
        LocalTime maintenant = LocalTime.now();
        return horaires.stream()
                .filter(h -> h.getJour() == aujourdhui)
                .anyMatch(h -> estDansPlageHoraire(maintenant, h.getHeureOuverture(), h.getHeureFermeture()));
    }

    /**
     * Un restaurant est commandable si : statut ACTIF, disponible, et au moins un horaire est ouvert maintenant.
     */
    public boolean estCommandable() {
        return statut == StatutRestaurant.ACTIF
                && Boolean.TRUE.equals(disponible)
                && estOuvertMaintenant();
    }

    /**
     * Le client est livrable s'il existe au moins une zone active avec un rayon valide (> 0)
     * couvrant la distance entre l'adresse du restaurant et la position client.
     */
    public boolean estLivrablePourClient(double latitudeClient, double longitudeClient) {
        if (adresse == null || adresse.getLatitude() == null || adresse.getLongitude() == null) {
            return false;
        }
        if (zonesLivraison == null || zonesLivraison.isEmpty()) {
            return false;
        }
        return zonesLivraison.stream()
                .filter(z -> Boolean.TRUE.equals(z.getDisponible()))
                .filter(z -> z.getRayonKm() != null && z.getRayonKm() > 0)
                .anyMatch(z -> distanceKm(
                                adresse.getLatitude(),
                                adresse.getLongitude(),
                                latitudeClient,
                                longitudeClient)
                        <= z.getRayonKm());
    }

    /**
     * Un restaurant est commandable pour un client si les règles globales sont vraies et que le client
     * est dans une zone de livraison active.
     */
    public boolean estCommandablePourClient(double latitudeClient, double longitudeClient) {
        return estCommandable() && estLivrablePourClient(latitudeClient, longitudeClient);
    }

    private static JourSemaine jourDepuisDayOfWeek(DayOfWeek jour) {
        return switch (jour) {
            case MONDAY -> JourSemaine.LUNDI;
            case TUESDAY -> JourSemaine.MARDI;
            case WEDNESDAY -> JourSemaine.MERCREDI;
            case THURSDAY -> JourSemaine.JEUDI;
            case FRIDAY -> JourSemaine.VENDREDI;
            case SATURDAY -> JourSemaine.SAMEDI;
            case SUNDAY -> JourSemaine.DIMANCHE;
        };
    }

    /**
     * Plage [début, fin] le même jour, ou chevauchement minuit si début &gt; fin.
     */
    private static boolean estDansPlageHoraire(LocalTime maintenant, LocalTime debut, LocalTime fin) {
        if (!debut.isAfter(fin)) {
            return !maintenant.isBefore(debut) && !maintenant.isAfter(fin);
        }
        return !maintenant.isBefore(debut) || !maintenant.isAfter(fin);
    }

    private static double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double rayonTerreKm = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return rayonTerreKm * c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
