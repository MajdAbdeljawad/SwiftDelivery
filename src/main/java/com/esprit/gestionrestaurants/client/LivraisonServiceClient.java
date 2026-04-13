package com.esprit.gestionrestaurants.client;

import com.esprit.gestionrestaurants.client.dto.ZoneLivraisonDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gestion-livraison")
public interface LivraisonServiceClient {

    @GetMapping("/api/zones")
    List<ZoneLivraisonDto> getAllZones();
}
