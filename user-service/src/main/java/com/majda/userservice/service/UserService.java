package com.majda.userservice.service;

import com.majda.userservice.entity.AppUser;
import com.majda.userservice.repository.AppUserRepository;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserService(AppUserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    public AppUser create(AppUser user) {
        user.setId(null);
        AppUser saved = userRepository.save(user);
        rabbitTemplate.convertAndSend("food.events.exchange", "user.created", "user-created:" + saved.getId());
        return saved;
    }

    public AppUser update(Long id, AppUser user) {
        AppUser existing = findById(id);
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setAddress(user.getAddress());
        return userRepository.save(existing);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @RabbitListener(queues = "user-service.order-events.queue")
    public void consumeOrderEvents(String payload) {
        System.out.println("[USER-SERVICE] Order event received: " + payload);
    }
}
