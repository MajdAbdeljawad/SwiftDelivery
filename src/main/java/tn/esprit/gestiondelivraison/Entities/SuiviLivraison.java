package tn.esprit.gestiondelivraison.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuiviLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSuivi;

    private String etat;

    private String commentaire;

    private Date dateMiseAJour;

    @ManyToOne
    private Livraison livraison;
}