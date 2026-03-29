package com.majda.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange foodEventsExchange() {
        return new TopicExchange("food.events.exchange");
    }

    @Bean
    public Queue userOrderEventsQueue() {
        return new Queue("user-service.order-events.queue", true);
    }

    @Bean
    public Binding userOrderEventsBinding(Queue userOrderEventsQueue, TopicExchange foodEventsExchange) {
        return BindingBuilder.bind(userOrderEventsQueue).to(foodEventsExchange).with("order.*");
    }
}
