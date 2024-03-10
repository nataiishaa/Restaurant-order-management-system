package com.developer.Restaurant_manager_appIDZ.controllers;
import com.developer.Restaurant_manager_appIDZ.model.Dish;
import com.developer.Restaurant_manager_appIDZ.model.Order;
import com.developer.Restaurant_manager_appIDZ.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.developer.Restaurant_manager_appIDZ.model.Status;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody List<String> dishNames, @RequestParam Integer userId) {
        String response = orderService.createOrder(dishNames, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Добавление блюда в заказ
    @PostMapping("/{orderId}/addDish")
    public ResponseEntity<String> addDishToOrder(@PathVariable Integer orderId, @RequestParam Integer dishId, @RequestParam Integer userId) {
        String response = orderService.addDishToOrder(orderId, dishId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Отмена заказа
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId, @RequestParam Integer userId) {
        String response = orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeOrder(@PathVariable Integer id) {
        String response = orderService.removeOrder(id);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer id, @RequestBody Status status) {
        String response = orderService.updateOrderStatus(id, status);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/orders/{orderId}/pay")
    public ResponseEntity<String> payOrder(@PathVariable Integer orderId, @RequestParam Integer userId, @RequestParam double cost) {
        String response = orderService.payForOrder(orderId, userId, cost);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {
        double revenue = orderService.calculateRevenue();
        return ResponseEntity.ok(revenue);
    }
    @GetMapping("/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam Status status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }



}


