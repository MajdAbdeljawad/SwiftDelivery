package tn.esprit.gestiondelivraison.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivreur;

    private String nom;

    private String prenom;

    private String telephone;

    private boolean disponible;

    private Long idVehicule;

    private Long idZone;

}