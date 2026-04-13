package com.esprit.gestionrestaurants.messaging;

import com.esprit.gestionrestaurants.config.RabbitMQConfig;
import com.esprit.gestionrestaurants.messaging.dto.RestaurantStatusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RestaurantStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    public RestaurantStatusProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(RestaurantStatusMessage message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
    }
}
