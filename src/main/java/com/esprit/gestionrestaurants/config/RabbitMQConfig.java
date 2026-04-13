package com.esprit.gestionrestaurants.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "restaurant.exchange";
    public static final String QUEUE = "restaurant.status.queue";
    public static final String ROUTING_KEY = "restaurant.status.changed";

    @Bean
    public DirectExchange restaurantExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue restaurantStatusQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding restaurantStatusBinding(Queue restaurantStatusQueue, DirectExchange restaurantExchange) {
        return BindingBuilder.bind(restaurantStatusQueue).to(restaurantExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
