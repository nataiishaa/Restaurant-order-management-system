package com.developer.Restaurant_manager_appIDZ.repository;

import com.developer.Restaurant_manager_appIDZ.model.Status;

import com.developer.Restaurant_manager_appIDZ.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer userId);
    List<Order> findAllByStatus(Status status);

}
