package com.majda.orderservice.config;

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
    public Queue orderUserEventsQueue() {
        return new Queue("order-service.user-events.queue", true);
    }

    @Bean
    public Binding orderUserEventsBinding(Queue orderUserEventsQueue, TopicExchange foodEventsExchange) {
        return BindingBuilder.bind(orderUserEventsQueue).to(foodEventsExchange).with("user.*");
    }
}
