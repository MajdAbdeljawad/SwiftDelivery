package tn.esprit.jungle.gestionrestaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.esprit.gestionrestaurants")
@SpringBootApplication(scanBasePackages = {
        "tn.esprit.jungle.gestionrestaurants",
        "com.esprit.gestionrestaurants"
})
@EntityScan(basePackages = "com.esprit.gestionrestaurants.entity")
@EnableJpaRepositories(basePackages = "com.esprit.gestionrestaurants.repository")
public class GestionRestaurantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionRestaurantsApplication.class, args);
    }

}
