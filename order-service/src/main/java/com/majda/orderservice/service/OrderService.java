package com.majda.orderservice.service;

import com.majda.orderservice.client.UserClient;
import com.majda.orderservice.entity.FoodOrder;
import com.majda.orderservice.repository.FoodOrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final FoodOrderRepository orderRepository;
    private final UserClient userClient;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(FoodOrderRepository orderRepository, UserClient userClient, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<FoodOrder> findAll() {
        return orderRepository.findAll();
    }

    public FoodOrder findById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public FoodOrder create(FoodOrder order) {
        userClient.getUserById(order.getUserId());

        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        FoodOrder saved = orderRepository.save(order);

        rabbitTemplate.convertAndSend("food.events.exchange", "order.created", "order-created:" + saved.getId());
        return saved;
    }

    public FoodOrder update(Long id, FoodOrder order) {
        FoodOrder existing = findById(id);
        userClient.getUserById(order.getUserId());

        existing.setUserId(order.getUserId());
        existing.setRestaurantName(order.getRestaurantName());
        existing.setStatus(order.getStatus());
        existing.setTotalPrice(order.getTotalPrice());

        FoodOrder updated = orderRepository.save(existing);
        rabbitTemplate.convertAndSend("food.events.exchange", "order.updated", "order-updated:" + updated.getId());
        return updated;
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found: " + id);
        }
        orderRepository.deleteById(id);
    }

    @RabbitListener(queues = "order-service.user-events.queue")
    public void consumeUserEvents(String payload) {
        System.out.println("[ORDER-SERVICE] User event received: " + payload);
    }
}
