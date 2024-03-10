package com.developer.Restaurant_manager_appIDZ.repository;



import com.developer.Restaurant_manager_appIDZ.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    Optional<Dish> findByName(String name);
}
