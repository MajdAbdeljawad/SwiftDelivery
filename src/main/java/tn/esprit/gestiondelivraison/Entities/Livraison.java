package tn.esprit.gestiondelivraison.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivraison;

    private Long idCommande;

    private Long idLivreur;

    private String adresseLivraison;

    private Date dateCreation;

    private Date dateLivraison;

    private Long restaurantId;

    private Double latitudeClient;
    private Double longitudeClient;

    @Enumerated(EnumType.STRING)
    private StatutLivraison statut;

    private String commentaire;

    @OneToMany(mappedBy = "livraison", cascade = CascadeType.ALL)
    private List<SuiviLivraison> suivis;
}