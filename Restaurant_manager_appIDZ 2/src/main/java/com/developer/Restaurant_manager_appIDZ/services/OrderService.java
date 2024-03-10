package com.developer.Restaurant_manager_appIDZ.services;



import com.developer.Restaurant_manager_appIDZ.model.Dish;
import com.developer.Restaurant_manager_appIDZ.model.Order;
import com.developer.Restaurant_manager_appIDZ.model.Status;
import com.developer.Restaurant_manager_appIDZ.repository.DishRepository;
import com.developer.Restaurant_manager_appIDZ.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Scope("singleton")
public class OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final CookingService cookingService;


    @Transactional
    public String createOrder(List<String> dishNames, Integer userId) {
        List<Dish> dishes = new ArrayList<>();
        for (String name : dishNames) {
            Optional<Dish> dishOptional = dishRepository.findByName(name);
            if (dishOptional.isPresent()) {
                Dish dish = dishOptional.get();
                if (dish.getQuantity() > 0) { // Проверяем, что блюда достаточно
                    dish.setQuantity(dish.getQuantity() - 1); // Уменьшаем количество на 1
                    dishes.add(dish);
                } else {
                    return "ERROR: Not enough " + name + " in stock";
                }
            } else {
                return "ERROR: Dish " + name + " is not available in the menu";
            }
        }
        if (dishes.isEmpty()) {
            return "ERROR: No valid dishes provided";
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setDishes(dishes);
        order.setStatus(Status.CREATED);
        dishRepository.saveAll(dishes); // Обновляем количество блюд в базе данных
        orderRepository.save(order);
        cookingService.addOrderToQueue(order, this::saveReadyOrder);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotalPrice(totalPrice);

        return "Order is processing";
    }

    @Transactional
    public String cancelOrder(Integer orderId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Impossible to cancel non-existing order";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.CREATED && order.getStatus() != Status.PREPARING) {
            return "ERROR: It is impossible to cancel order that is not in process";
        }
        // Восстановление количества блюд
        order.getDishes().forEach(dish -> {
            dish.setQuantity(dish.getQuantity() + 1);
            dishRepository.save(dish);
        });

        order.setStatus(Status.CANCELED);
        orderRepository.save(order);
        return "Order successfully canceled";
    }

    @Transactional
    public String addDishToOrder(Integer orderId, Integer dishId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Impossible to update non-existing order";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.CREATED && order.getStatus() != Status.PREPARING) {
            return "ERROR: It is impossible to add dish to an order that is not in process";
        }
        if (!dishRepository.existsById(dishId)) {
            return "ERROR: Dish is not available in the menu";
        }
        Dish dish = dishRepository.getReferenceById(dishId);
        order.getDishes().add(dish);
        orderRepository.save(order);
        return "Dish successfully added to order";
    }
@Transactional
    public String getOrderStatus(Integer orderId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Unable to get status of non-existent order";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        return order.getStatus().name();
    }
    @Transactional
    public String payForOrder(Integer orderId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Unable to pay for non-existent order";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.READY) {
            return "ERROR: Unable to pay for a non-ready order";
        }
        //order.setPaymentAmount(paymentAmount); // Установить сумму оплаты
        //order.setStatus(Status.PAID); // Изменить статус заказа на оплаченный
        orderRepository.save(order);
        return "Order successfully paid";
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional
    public String tryUpdateOrderStatus(Integer orderId, Status newStatus, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Unauthorized access to the order";
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
        return String.format("Order status updated to %s successfully", newStatus);
    }

    @Transactional
    public String removeOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order to delete not found";
        }
        orderRepository.deleteById(id);
        return "Order successfully deleted";
    }

    @Transactional
    public String updateOrderStatus(Integer orderId, Status status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order to update not found";
        }
        Order order = orderOptional.get();
        order.setStatus(status);
        orderRepository.save(order);
        return "Order successfully updated";
    }

    @Transactional
    public Void saveReadyOrder(Order order){
        orderRepository.save(order);
        return null;
    }
    @Transactional
    public String payForOrder(Integer orderId, Integer userId, double paymentAmount) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Unable to pay for non-existent order";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.READY) {
            return "ERROR: Unable to pay for a non-ready order";
        } else {
            if (order.getTotalPrice() > paymentAmount){
                return "ERROR: not enough money to pay";
            }
            order.setStatus(Status.PAYED);
        }
        if (paymentAmount < order.getTotalPrice()) {
            return "ERROR: Payment amount is less than the total price";
        }

        order.setStatus(Status.PAYED);
        orderRepository.save(order);
        return "Order successfully paid";
    }
    public double calculateRevenue() {
        List<Order> paidOrders = orderRepository.findAllByStatus(Status.PAYED);
        return paidOrders.stream().mapToDouble(Order::getTotalPrice).sum();
    }
    // Метод для получения заказов по статусу
    public List<Order> getOrdersByStatus(Status status) {
        return orderRepository.findAllByStatus(status);
    }


}