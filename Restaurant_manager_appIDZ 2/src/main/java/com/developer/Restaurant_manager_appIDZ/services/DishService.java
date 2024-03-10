package com.developer.Restaurant_manager_appIDZ.services;

import com.developer.Restaurant_manager_appIDZ.model.Dish;
import com.developer.Restaurant_manager_appIDZ.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    @Transactional
    public String addDishItem(Dish dish) {
        if (dishRepository.findByName(dish.getName()).isPresent()) {
            return "ERROR: Dish item was already added";
        }
        dishRepository.save(dish);
        return "New dish item successfully added";
    }

    @Transactional
    public String removeDishItem(Integer dishId) {
        Optional<Dish> dishOptional = dishRepository.findById(dishId);
        if (dishOptional.isEmpty()) {
            return "ERROR: Dish to delete not found";
        }
        dishRepository.deleteById(dishId);
        return "Dish item successfully deleted";
    }

    @Transactional
    public String updateDishItem(Integer dishId, Integer cookTime, Double price, Integer quantity) {
        Optional<Dish> dishOptional = dishRepository.findById(dishId);
        if (dishOptional.isEmpty()) {
            return "ERROR: Dish to update not found";
        }
        Dish dish = dishOptional.get();
        if (cookTime != null) {
            dish.setCookTime(cookTime);
        }
        if (price != null) {
            dish.setPrice(price);
        }
        if (quantity != null){
            dish.setQuantity(quantity);
        }
        dishRepository.save(dish);
        return "Dish successfully updated";
    }

    public Dish getById(Integer id) {
        return dishRepository.findById(id).orElse(null);
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }
}
