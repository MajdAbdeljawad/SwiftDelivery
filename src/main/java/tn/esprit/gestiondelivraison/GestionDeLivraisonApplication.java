package tn.esprit.gestiondelivraison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class GestionDeLivraisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDeLivraisonApplication.class, args);
    }

}
