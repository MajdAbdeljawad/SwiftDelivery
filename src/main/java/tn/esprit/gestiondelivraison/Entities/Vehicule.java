package tn.esprit.gestiondelivraison.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String type;

    private String immatriculation;

    private String couleur;

    private boolean disponible;
}