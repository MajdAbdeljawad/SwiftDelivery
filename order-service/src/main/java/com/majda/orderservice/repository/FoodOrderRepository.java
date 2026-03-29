package com.majda.orderservice.repository;

import com.majda.orderservice.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}
