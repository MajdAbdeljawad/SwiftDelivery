package tn.esprit.gestiondelivraison.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idZone;

    private String nomZone;

    private String ville;

    private String codePostal;

    private boolean active;
}