package com.esprit.gestionrestaurants.client.dto;

public class ZoneLivraisonDto {

    private Long idZone;
    private String nomZone;
    private String ville;
    private String codePostal;
    private boolean active;

    public ZoneLivraisonDto() {
    }

    public ZoneLivraisonDto(Long idZone, String nomZone, String ville, String codePostal, boolean active) {
        this.idZone = idZone;
        this.nomZone = nomZone;
        this.ville = ville;
        this.codePostal = codePostal;
        this.active = active;
    }

    public Long getIdZone() {
        return idZone;
    }

    public void setIdZone(Long idZone) {
        this.idZone = idZone;
    }

    public String getNomZone() {
        return nomZone;
    }

    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
