package com.majda.orderservice.controller;

import com.majda.orderservice.entity.FoodOrder;
import com.majda.orderservice.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<FoodOrder> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public FoodOrder getById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrder create(@Valid @RequestBody FoodOrder order) {
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    public FoodOrder update(@PathVariable Long id, @Valid @RequestBody FoodOrder order) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
