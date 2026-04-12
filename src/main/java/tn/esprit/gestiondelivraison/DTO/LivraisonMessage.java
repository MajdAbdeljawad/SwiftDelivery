package tn.esprit.gestiondelivraison.DTO;


import java.io.Serializable;

public class LivraisonMessage implements Serializable {

        private Long livraisonId;
        private Long restaurantId;
        private String adresseClient;
        private Double latitudeClient;
        private Double longitudeClient;

        public LivraisonMessage() {}

        public LivraisonMessage(Long livraisonId, Long restaurantId, String adresseClient, Double latitudeClient, Double longitudeClient) {
            this.livraisonId = livraisonId;
            this.restaurantId = restaurantId;
            this.adresseClient = adresseClient;
            this.latitudeClient = latitudeClient;
            this.longitudeClient = longitudeClient;
        }

        // getters et setters
        public Long getLivraisonId() { return livraisonId; }
        public void setLivraisonId(Long livraisonId) { this.livraisonId = livraisonId; }

        public Long getRestaurantId() { return restaurantId; }
        public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

        public String getAdresseClient() { return adresseClient; }
        public void setAdresseClient(String adresseClient) { this.adresseClient = adresseClient; }

        public Double getLatitudeClient() { return latitudeClient; }
        public void setLatitudeClient(Double latitudeClient) { this.latitudeClient = latitudeClient; }

        public Double getLongitudeClient() { return longitudeClient; }
        public void setLongitudeClient(Double longitudeClient) { this.longitudeClient = longitudeClient; }

        @Override
        public String toString() {
            return "LivraisonMessage{" +
                    "livraisonId=" + livraisonId +
                    ", restaurantId=" + restaurantId +
                    ", adresseClient='" + adresseClient + '\'' +
                    ", latitudeClient=" + latitudeClient +
                    ", longitudeClient=" + longitudeClient +
                    '}';
        }
    }